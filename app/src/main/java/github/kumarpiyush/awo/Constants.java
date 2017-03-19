package github.kumarpiyush.awo;

public class Constants {
    static final String errorDisplayMessageKey = "ErrorDisplayMessage";
    public static final String accountType = "github.kumarpiyush.awo";

    public class Owa {
        public static final String clientId = "0dfbbbca-335f-4e58-8e23-d3d0d8281b72";

        public static final String redirectUrl = "http://localhost/awo/";
        public static final String scope = "offline_access https://outlook.office.com/mail.read";

        public static final String authorizationUrl = "https://login.microsoftonline.com/common/oauth2/v2.0/authorize";
        public static final String tokenUrl = "https://login.microsoftonline.com/common/oauth2/v2.0/token";
    }

    class Sync {
        static final long syncIntervalInMilliseconds = 15 * 60 * 1000; // 15 minutes
    }
}
