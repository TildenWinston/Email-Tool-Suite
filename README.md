# Email-Tool-Suite
Tools to better manage email. Email stats, email expiration, and more in the works.


Authentication Failing?
Try deleting StoredCredentials and re-authorizing.

Planned features:
* GUI - in progress in GUI branch
* Switch from milliseconds to days in input file
* Better separate methods
* Introduce greater code reuse
* Add test cases
* Create graphs from count or size data
* Auto-run on set interval
* Switch from "before" to "older_than"

How to set up (Incomplete):
Download project, ensure java and Gradle are both installed (See: https://developers.google.com/gmail/api/quickstart/java)

Put your credentials that you downloaded from the Gmail API dashboard into resources.

Should be similar to:

Email-Tool-Suite
    
    src
        main
            resources
                credentials.json
                input.txt

input.txt is how AutoRead.java accepts settings.

input.txt should look like:

    Days before: (in milliseconds, i.e. 8000000000 = ~90 days
    8000000000

    Unread? (unread or read)
    unread

    Test Run? (yes or no) - By setting test run to yes, changes will be undone and not cause any changes. Logging and counts will appear like normal
    no
    
    Addresses to look at:
    email@example.com
    email2@example.com
    business.com

    Search stings to look at: (Full formatted. Nothing will be added to these. Good for emails that come from multiple addresses, but have another common thread.)
    subject:(Newsletter) is:unread before:2019/04/30

Run AutoRead.java to automatically mark emails as read. Size.java and Count.java can also be run and they don't make any account changes so they are good places to start.

