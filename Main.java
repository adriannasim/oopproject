import java.util.Scanner;

public class Main {   
    public static void main(String[] args) throws Exception{
        //variables declaration
        boolean loop = true;
        //methods declaration
        Scanner input = new Scanner(System.in); //scanner
        Login login = new Login();

         System.out.printf("\r\n" + //
                 "··················································\r\n" + //
                 "··············csCSSSSGGGGGGGGG····················\r\n" + //
                 "·············cccccccccGGGGGGGGG···················\r\n" + //
                 "·······u·qqQQQQQQpssGGGGGGGGGG····················\r\n" + //
                 "·······QQQQQQQQQQb································\r\n" + //
                 "·······QQQQQQQQQQC································\r\n" + //
                 "········QQQQQQQQC············sQQQQQQQQQQQQQQQQQQQ·\r\n" + //
                 "·········SQQQQP···············sQQQQQQQQQQQQQQQGQ··\r\n" + //
                 "··········QQQQC···············sQQQQQQQQGQQQQQQQQ··\r\n" + //
                 "··········QQQQC···············sQQQQQC·····QQQQQQ··\r\n" + //
                 "··········QQQQC··············udQQQQQC·····QQQQQQ··\r\n" + //
                 "········dQQQQQQQQQQQQQQQQQQQQQQQQQQQC·····QQQQQQ··\r\n" + //
                 "········QQQQQQQQQQQQQQQQQQQQQQQQQQQQbpppppQQQQQQ··\r\n" + //
                 "······sQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ··\r\n" + //
                 "········QQQQQQQQQQQQQQQQQQQQQQPPPPPPPPPPPPPPPPPG··\r\n" + //
                 "········sQQQQQQbQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQp··\r\n" + //
                 "·········QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQb··\r\n" + //
                 "·········QQQQQQQQQQQQQGbbQQQQQQQQQQQQQQQQQQQQQQb··\r\n" + //
                 "······dppQQQQQQQQQQQQQQQbQQGGQQQQpQQQQQQpQPQQpQb··\r\n" + //
                 "·····QQQGPQQQQQQQQQQQQPPSQPGGQQGGGQpGQGGGQQGGGQb··\r\n" + //
                 "···dQQQQCQQQQQQQQQQQQQb··QQ·QCSQ·Qb··QQuQC·Q·QQ···\r\n" + //
                 "··QQGQQQu·bQQQP··SQQQP····PbQQQpPC····PbQQQQPP····\r\n" + //
                 "    \r\n");
        System.out.println("==================================================");
        System.out.println("        WELCOME TO TRAIN TICKETING SYSTEM");
        do {
            System.out.println("==================================================");
            System.out.println("                       Menu");
            System.out.println("==================================================");
            System.out.printf("1. Customer Login Menu\n");
            System.out.printf("2. Staff Login Menu\n");
            System.out.println("\n* Press # to exit");
            System.out.println("==================================================");
            System.out.print("Enter your option > ");

            //accept user input
            if (input.hasNextInt()) {
                int choice = input.nextInt();

                //choose user's input choice
                switch (choice) {
                //login menus
                case 1:
                case 2:
                    //clear screen
                    for (int i = 0; i < 100; i++) {
                        System.out.println();
                    }
                    login.driverLogin(choice);
                    break;
                default:
                    //clear screen
                    for (int i = 0; i < 100; i++) {
                        System.out.println();
                    }
                    System.out.printf("Invalid input, please enter your choice again.\n");
                }
            } else {
                //quit
                if (input.next().equals("#")) {
                    boolean loop2 = true;
                    do {
                        System.out.printf("Are you sure you want to quit (Y/N)? > ");
                        String choice1 = input.next();
                        if (choice1.length() == 1) {
                            switch (choice1.charAt(0)) {
                                //confirm quit
                                case 'Y':
                                case 'y':
                                    //clear screen
                                    for (int i = 0; i < 100; i++) {
                                        System.out.println();
                                    }
                                    System.out.printf("\r\n" + //
                                            "    ·····························dbp··················\r\n" + //
                                            "    ···················sGs·····s···SQp················\r\n" + //
                                            "    ···········ss····sQGGGGQ····SQp··SQu··············\r\n" + //
                                            "    ·········QGGGGQp··QQGGGGGb····SQ··SQb·············\r\n" + //
                                            "    ·········SQQGGGGQu·SQQGGGGQc···SQc··Qb············\r\n" + //
                                            "    ···········bQQGGGGQu·SQGGGGGb···sb··cP····sQGGGGQc\r\n" + //
                                            "    ·······ssss··bQGGGGGQ·cQQGGGGGp··········QGGGGGQp·\r\n" + //
                                            "    ······QGGGGGQ·CbQGGGGGQ·SQQGGGGQc·······QGGGGGQp··\r\n" + //
                                            "    ·······QQGGGGGQ·cQQGGGGGb·SQGGGGGQ······QGGGGGQ···\r\n" + //
                                            "    ·········QQGGGGGQcSQQGGGGGbQQGGGGGGQpuqQGGGGGQP···\r\n" + //
                                            "    ··········SQQGGGGGQuSQQGGGGSSGGGGGGGGGGGCGGGGQC···\r\n" + //
                                            "    ············SbQGGGGGQuSQQGCCGGGGGGGGGGGCCGGGGQQ···\r\n" + //
                                            "    ·······sGGGGQuCbQGGGGGGQGGGGGGGGGGGGGGCCCCGGGGQC··\r\n" + //
                                            "    ·······sQQGGGGGpCbQQGGGGGGGGGGGGGGGGGGCCCCGGGGQC··\r\n" + //
                                            "    ····SQ···SQQGGGGGbCSQQGCGGGGGGGGGGGGGGCCCCGGGGQQ··\r\n" + //
                                            "    ·Qb··SQp···SbQQGGGGGQGGGGGGGGGGGGGGGGGGCGGGGGQQQ··\r\n" + //
                                            "    ··SQp··SQp····SQQGGGGGGGGGGGGGGGGGGGGGGGGGGQQQQP··\r\n" + //
                                            "    ····bQp··CPQC···PQQQGGGGCCCCCCCGGGGGGGGGQQQQQQb···\r\n" + //
                                            "    ······SQbp·········SQQGGGGCCCCCCCGGGGGGQQQQQQP····\r\n" + //
                                            "    ·········PP··········PQQQGGGGGGGGGGGQQQQQQQPC·····\r\n" + //
                                            "    ························SQQQQQQQQQQQQQQQQP········\r\n" + //
                                            "    ··························CPGQQQQQQQQPPC··········\r\n" + //
                                            "    \r\n");
                                    System.out.printf("                       Bye bye!");
                                    input.close();
                                    System.exit(0);
                                    break;
                                //cancel quit
                                case 'N':
                                case 'n':
                                    loop2 = false;
                                    //clear screen
                                    for (int i = 0; i < 100; i++) {
                                        System.out.println();
                                    }
                                    break;
                                default: 
                                    //clear screen
                                    for (int i = 0; i < 100; i++) {
                                        System.out.println();
                                    }
                                    System.out.printf("Invalid input, please enter your choice again.\n\n");
                            }
                        } else {
                            //clear screen
                            for (int i = 0; i < 100; i++) {
                                System.out.println();
                            }
                            System.out.printf("Invalid input, please enter your choice again.\n\n");
                        }
                    } while (loop2);
                } else {
                    //clear screen
                    for (int i = 0; i < 100; i++) {
                        System.out.println();
                    }
                    System.out.printf("Invalid input, please enter your choice again.\n\n");
                    //clear buffer
                    input.next();
                }
            }
        } while (loop);
    }  
}
