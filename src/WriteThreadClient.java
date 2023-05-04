import util.NetworkUtil;

import java.util.Scanner;

public class WriteThreadClient extends Thread {
    private NetworkUtil networkUtil;
    private String clientName;

    public WriteThreadClient(NetworkUtil networkUtil, String clientName) {
        this.networkUtil = networkUtil;
        this.clientName = clientName;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter recipient name and message (format: recipient,message): ");
            String input = scanner.nextLine();
            String[] parts = input.split(",", 2);
            if (parts.length != 2) {
                System.out.println("Invalid input format.");
                continue;
            }
            Message message = new Message();
            message.setFrom(clientName);
            message.setTo(parts[0]);
            message.setText(parts[1]);

            try {
                networkUtil.write(message);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
