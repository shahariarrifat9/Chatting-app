import util.NetworkUtil;

public class ReadThreadClient extends Thread {
    private NetworkUtil networkUtil;

    public ReadThreadClient(NetworkUtil networkUtil) {
        this.networkUtil = networkUtil;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Message message = (Message) networkUtil.read();
                if (message != null) {
                    if ("server".equalsIgnoreCase(message.getFrom())) {
                        String[] inboxMessages = message.getText().split("~");
                        System.out.println("Your Inbox:");
                        for (String inboxMessage : inboxMessages) {
                            System.out.println(inboxMessage);
                        }
                    } else {
                        System.out.println("From: " + message.getFrom() + " Message: " + message.getText());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
