import java.util.HashMap;

public class ReadThreadServer extends Thread {
    private HashMap<String, NetworkInformation> clientNetworkInformationMap;
    private NetworkInformation senderNetworkInformation;

    public ReadThreadServer(HashMap<String, NetworkInformation> clientNetworkInformationMap, NetworkInformation senderNetworkInformation) {
        this.clientNetworkInformationMap = clientNetworkInformationMap;
        this.senderNetworkInformation = senderNetworkInformation;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Message message = (Message) senderNetworkInformation.getNetworkUtil().read();
                if (message != null) {
                    String receiverName = message.getTo();
                    NetworkInformation receiverNetworkInformation = clientNetworkInformationMap.get(receiverName);

                    if ("server".equalsIgnoreCase(receiverName) && "inbox".equalsIgnoreCase(message.getText())) {
                        StringBuilder inboxMessages = new StringBuilder();
                        for (String inboxMessage : senderNetworkInformation.getInbox()) {
                            inboxMessages.append(inboxMessage).append("~");
                        }

                        Message serverMessage = new Message();
                        serverMessage.setFrom("server");
                        serverMessage.setTo("client");
                        serverMessage.setText(inboxMessages.toString());

                        senderNetworkInformation.getNetworkUtil().write(serverMessage);
                    } else {
                        receiverNetworkInformation.getInbox().add("From: " + message.getFrom() + " Message: " + message.getText());
                        receiverNetworkInformation.getNetworkUtil().write(message);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
