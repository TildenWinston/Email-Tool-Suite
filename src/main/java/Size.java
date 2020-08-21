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
import com.google.api.services.gmail.model.Message;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.*;
import java.io.IOException;

public class Size {
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
        InputStream in = GmailQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
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

        return messages;
    }

    public static void main(String... args) throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        // Print the labels in the user's account.
        String user = "me";


//        ListLabelsResponse listResponse = service.users().labels().list(user).execute();
//        List<Label> labels = listResponse.getLabels();
//        if (labels.isEmpty()) {
//            System.out.println("No labels found.");
//        } else {
//            System.out.println("Labels:");
//            for (Label label : labels) {
//                System.out.printf("- %s\n", label.getName());
//            }
//        }

        // Search queries
        ArrayList<String> searchQueries = new ArrayList<>();
//        searchQueries.add("from:(email@washingtonpost.com) is:unread");
//        searchQueries.add("from:(noreply@youtube.com) is:unread");
//        searchQueries.add("from:(leadership@smartbrief.com) is:unread");
//        searchQueries.add("from:(admin@cintindex.com) is:unread");
//        searchQueries.add("from:(republic@am.republicwireless.com) is:unread");
//        searchQueries.add("from:(contact@mailer.humblebundle.com) is:unread");
//        searchQueries.add("from:(quest@quest.com) is:unread");
//        searchQueries.add("from:(nytdirect@nytimes.com) is:unread");
//        searchQueries.add("from:(daily.report@mc.virginia.edu) is:unread");
//        searchQueries.add("from:(unitrendsteam@unitrends.com) is:unread");
//        searchQueries.add("from:(noreply@pearlizumi.com) is:unread");
//        searchQueries.add("from:(no-reply@coinbase.com) is:unread");
//        searchQueries.add("from:(calendar-notification@google.com) is:unread");
//        searchQueries.add("from:(newsletter@mindsumo.com) is:unread");
//        searchQueries.add("subject:(Daily Skimm) is:unread ");
//        searchQueries.add("from:(gog.com) is:unread");
//        searchQueries.add("from:(comptia@smartbrief.com) is:unread");
//         searchQueries.add("from:(info@vast.gg) is:unread");
//         searchQueries.add("from:(pluralsight.com) is:unread");
//         searchQueries.add("from:(goodpolitics@virginia.edu) is:unread");
//         searchQueries.add("from:(umidigi.com) is:unread");
//         searchQueries.add("from:(action@ifttt.com) is:unread");
//        searchQueries.add("from:(@linkedin.com) is:unread");
//        searchQueries.add("from:(no-reply@strava.com) is:unread");
//         searchQueries.add("from:(microsoftrewards@e.microsoft.com) is:unread");
//         searchQueries.add("from:(emails@songkick.com) is:unread");
//         searchQueries.add("from:(informatica@reply.informatica-news.com) is:unread");
//         searchQueries.add("from:(info@send.grammarly.com) is:unread");
//         searchQueries.add("from:(team@fightforthefuture.org) is:unread");
//        searchQueries.add("from:(info@obama.org) is:unread");
//         searchQueries.add("from:(info@peopleforbikes.org) is:unread");


        //  searchQueries.add(" is:unread");
        //  searchQueries.add("is:read");
        // searchQueries.add("from:(noreply@youtube.com) is:unread");
        // searchQueries.add("from:(noreply@youtube.com) is:unread");
        // searchQueries.add("from:(noreply@youtube.com) is:unread");
        // searchQueries.add("from:(noreply@youtube.com) is:unread");
        // searchQueries.add("from:(noreply@youtube.com) is:unread");
        // searchQueries.add("from:(noreply@youtube.com) is:unread");
        // searchQueries.add("from:(noreply@youtube.com) is:unread");


        //Mark as read
        ModifyMessageRequest markRead = new ModifyMessageRequest().setRemoveLabelIds(Collections.singletonList("UNREAD"));
        ModifyMessageRequest markUnread = new ModifyMessageRequest().setAddLabelIds(Collections.singletonList("UNREAD"));
        ModifyMessageRequest nothing = new ModifyMessageRequest();

        //Loop through all search queries and add them to list
        List<Message> matched = listMessagesMatchingQuery(service, user, searchQueries.get(0));
        for(int i = 1   ; i < searchQueries.size()-1; i++) {
            matched.addAll(listMessagesMatchingQuery(service, user, searchQueries.get(i)));
        }

        System.out.println("Found all");
        long start = System.nanoTime();

        //System.out.println("Labels: " + matched.get(0).getLabelIds());
        //Message temp = service.users().messages().modify(user, matched.get(0).getId(), markRead).execute();
        //Message temp = service.users().messages().modify(user, matched.get(0).getId(), markUnread).execute();
        //Message temp = service.users().messages().modify(user, matched.get(0).getId(), nothing).execute();


        HashMap<String, Integer> messageCounts = new HashMap<>();

        HashMap<String, Long> senderSize = new HashMap<>();

        //Find all unique senders and count emails from each.
        for(Message mail: matched){
            //long oneRun = System.nanoTime();
            List<MessagePartHeader> heads = service.users().messages().get(user, mail.getId()).execute().getPayload().getHeaders();
            String sender = "";
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
//    Integer emails = messageCounts.get(sender);
//    if (emails == null) {
//        messageCounts.put(sender, 1);
//    } else {
//        messageCounts.put(sender, emails + 1);
//    }


                //Size by sender
                //  Message tempMess = service.users().messages().get(user, mail.getId()).execute().getSizeEstimate();

                int tempSize = service.users().messages().get(user, mail.getId()).execute().getSizeEstimate();



                Long emails = senderSize.get(sender);
                if (emails == null) {
                    senderSize.put(sender, tempSize + 0L);
                } else {
                    senderSize.put(sender, emails + tempSize);
                }


            }
            catch(Exception e){

            }
            //long oneRunEnd = System.nanoTime();
            //System.out.println("Per for loop run: " + (oneRunEnd - oneRun) + " P1: " + (p1 - oneRun) + " P2: " + (oneRunEnd - p1));
        }


        //System.out.println("HashMap: " + messageCounts);

        System.out.println("Evaluated: " + matched.size());

        //String outToFile = "" + messageCounts;
        String outToFile = "" + senderSize;

        FileWriter fileWriter = new FileWriter(".\\src\\main\\resources\\count7.txt");
        fileWriter.write(outToFile);
        fileWriter.close();

        long FinEnd = System.nanoTime();
        System.out.println("FinEnd: " + (FinEnd - start));


    }
}