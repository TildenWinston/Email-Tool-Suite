import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.*;

import java.io.*;
import java.security.GeneralSecurityException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class AutoRead {
    private static final String APPLICATION_NAME = "Gmail API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Arrays.asList(GmailScopes.GMAIL_LABELS, GmailScopes.GMAIL_MODIFY);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = GmailQuickstart2.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    /**
     * List all Messages of the user's mailbox matching the query.
     *
     * @param service Authorized Gmail API instance.
     * @param userId User's email address. The special value "me"
     * can be used to indicate the authenticated user.
     * @param query String used to filter the Messages listed.
     * @throws IOException
     */
    public static List<Message> listMessagesMatchingQuery(Gmail service, String userId,
                                                          String query) throws IOException {
        // System.out.println("userId: " + userId + " Query: " + query);

        ListMessagesResponse response = service.users().messages().list(userId).setQ(query).execute();

        List<Message> messages = new ArrayList<Message>();
        while (response.getMessages() != null) {
            messages.addAll(response.getMessages());
            if (response.getNextPageToken() != null) {
                String pageToken = response.getNextPageToken();
                response = service.users().messages().list(userId).setQ(query)
                        .setPageToken(pageToken).execute();
            } else {
                break;
            }
        }

//        for (Message message : messages) {
//            System.out.println(message.toPrettyString());
//        }
//        System.out.println(messages.size());

        return messages;
    }

    public static String getLabelId(List<Label> labels){
        try {
            for (int n = labels.size() - 1; n >= 0; n--) {
                //for(MessagePartHeader headPortion: heads){
                //System.out.println(headPortion.getName());
                Label tempLabel = labels.get(n);
                if (tempLabel.getName().equals("Auto Read")) {
                    return tempLabel.getId();
                }
            }
        } catch (Exception e) {

        }
        return "";
    }

    public static void main(String... args) throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        // Print the labels in the user's account.
        String user = "me";
        long start = System.nanoTime();

        ListLabelsResponse listResponse = service.users().labels().list(user).execute();
        List<Label> labels = listResponse.getLabels();
//        if (labels.isEmpty()) {
//            System.out.println("No labels found.");
//        } else {
//            System.out.println("Labels:");
//            for (Label label : labels) {
//                System.out.printf("- %s id: %s\n", label.getName(), label.getId());
//            }
//        }

        File file = new File(".\\src\\main\\resources\\input.txt");

        BufferedReader br = new BufferedReader(new FileReader(file));

        String platform = br.readLine();
        Long timeBefore = Long.parseLong(br.readLine());
        br.readLine();

        //unread
        br.readLine();
        String readOrUnread = br.readLine();

        //Addresses
        br.readLine();
        br.readLine();

        ArrayList<String> searchQueries = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        long currentDate = System.currentTimeMillis();

        String beforeafter = " before:" +  sdf.format(new Timestamp(System.currentTimeMillis() - timeBefore)); //yyyy/m/d

        System.out.println(beforeafter);

        String tempAdds = "";
        tempAdds = br.readLine();
        while(!tempAdds.equals("")){
            searchQueries.add("from:(" + tempAdds + ") is:" + readOrUnread + beforeafter);
            tempAdds = br.readLine();
        }

        //Search strings
        br.readLine();
        String tempSearch;
        tempSearch = br.readLine();
        while(!tempSearch.equals("")){
            searchQueries.add(tempSearch);
            tempSearch= br.readLine();
        }

        br.close();

        //Loop through all search queries and add them to list
        List<Message> matched = listMessagesMatchingQuery(service, user, searchQueries.get(0) + " " + beforeafter);
        for(int i = 1; i < searchQueries.size(); i++) {
            //System.out.println("query: " + searchQueries.get(i) + " " + beforeafter);
            matched.addAll(listMessagesMatchingQuery(service, user, searchQueries.get(i)));
        }

        long searchTime = System.nanoTime() - start;
        System.out.println("Found all");

        //Mark as read
        ModifyMessageRequest markRead = new ModifyMessageRequest().setRemoveLabelIds(Collections.singletonList("UNREAD"));
        // labels
        // get label id
        String autoReadLabelId = getLabelId(labels);
        markRead.setAddLabelIds(Collections.singletonList(autoReadLabelId));
        ModifyMessageRequest markUnread = new ModifyMessageRequest().setAddLabelIds(Collections.singletonList("UNREAD"));
        //ModifyMessageRequest markUnread = new ModifyMessageRequest().setAddLabelIds(Collections.singletonList("UNREAD"));
        ModifyMessageRequest nothing = new ModifyMessageRequest();

        List<String> MessagesRead = new ArrayList<String>() {};

        //service.users().messages().batchModify(matched, ,markUnread);


        for(int i = 0; i < matched.size(); i++) {
            //System.out.println("Labels: " + matched.get(0).getLabelIds());
            Message tempMessage = service.users().messages().modify(user, matched.get(i).getId(), markRead).execute();
            //System.out.println(i + " temp: " + tempMessage);

            List<MessagePartHeader> heads = service.users().messages().get(user, matched.get(i).getId()).execute().getPayload().getHeaders();
            String subject = "";
            try {
                for (int n = heads.size() - 1; n >= 0; n--) {
                    //for(MessagePartHeader headPortion: heads){
                    //System.out.println(headPortion.getName());
                    MessagePartHeader tempHeader = heads.get(n);
                    if (tempHeader.getName().equals("Subject")) {
                        subject = tempHeader.getValue();
                        break;
                    }
                }
                //System.out.println("Subject: " + subject + " Headers: " + tempMessage);
                MessagesRead.add("Subject: " + subject + " Headers: " + tempMessage);
            } catch (Exception e) {

            }

            //tempMessage = service.users().messages().modify(user, matched.get(i).getId(), markUnread).execute();
            //Message temp = service.users().messages().modify(user, matched.get(0).getId(), nothing).execute();
        }

        long readTime = System.nanoTime() - searchTime - start;

        HashMap<String, Integer> messageCounts = new HashMap<>();

        //HashMap<String, Long> senderSize = new HashMap<>();

        //Find all unique senders and count emails from each.
        for(Message mail: matched){
            //long oneRun = System.nanoTime();
            List<MessagePartHeader> heads = service.users().messages().get(user, mail.getId()).execute().getPayload().getHeaders();
            String sender = "";
            //System.out.println(mail.getSnippet());
            try {
                for (int i = heads.size() - 1; i >= 0; i--) {
                    //for(MessagePartHeader headPortion: heads){
                    //System.out.println(headPortion.getName());
                    MessagePartHeader temp = heads.get(i);
                    if (temp.getName().equals("From")) {
                        sender = temp.getValue();
                        //System.out.println("i: " + i);
                        break;
                    }
                }

                for (int n = sender.length() - 1; n >= 0; n--) {
                    if (sender.charAt(n) == '<') {
                        sender = sender.substring(n + 1, sender.length() - 1);
                        //System.out.println("Sender: " + sender);
                        break;
                    }
                }

                //long p1 = System.nanoTime();

                //Count by sender
                Integer emails = messageCounts.get(sender);
                if (emails == null) {
                    messageCounts.put(sender, 1);
                } else {
                    messageCounts.put(sender, emails + 1);
                }

            } catch (Exception e) {

            }
            //long oneRunEnd = System.nanoTime();
            //System.out.println("Per for loop run: " + (oneRunEnd - oneRun) + " P1: " + (p1 - oneRun) + " P2: " + (oneRunEnd - p1));
        }

        //System.out.println("HashMap: " + messageCounts);

        System.out.println("Matched: " + matched.size());

        String outToFile = "" + messageCounts;
        //String outToFile = "" + senderSize;
        outToFile = outToFile + "\n" + "Emails Read: \n" + MessagesRead;

        FileWriter fileWriter = new FileWriter(".\\src\\main\\resources\\count-" + currentDate + ".txt");

        fileWriter.write(outToFile);
        fileWriter.close();

        long loggingTime = System.nanoTime() - readTime - searchTime - start;
        long FinEnd = System.nanoTime();
        long elapsed = FinEnd - start;
        System.out.println("Search Time: " + (searchTime) + " In seconds: " + (searchTime/1000000000.0));
        System.out.println("Read Time: " + (readTime) + " In seconds: " + (readTime/1000000000.0));
        System.out.println("Logging Time: " + (loggingTime) + " In seconds: " + (loggingTime/1000000000.0));
        System.out.println("Total Elapsed Time: " + (elapsed) + " In seconds: " + (elapsed/1000000000.0));
    }
}
