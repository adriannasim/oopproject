public class test {
    public static void main(String[] args){
        System.out.println("====================================");
        System.out.println(" Update Train Information");
        System.out.println("====================================");
        System.out.print(" Enter train no to search the train > "); //<-------------
        userInput = scanner.nextLine();
        trainNo = Integer.parseInt(userInput);
        for (int i = 0; i < trainList.size(); i++) {
             if (trainNo==trainList.get(i).getTrainNo()) {
                    found = true;
             }
             if (found == true){
                System.out.println(" Do you want to update the train information as shown below ?");
                System.out.println(trainList.get(i).toString());
                do{
                    System.out.print(" Enter your option (Y-Yes/N-No) > ");
                    userInput = scanner.nextLine();
                    if(userInput.equalsIgnoreCase("Y")){
                        System.out.println(" The field that can be updated :");
                        System.out.println(" 1. Train Name");
                        System.out.println(" Press # to go back");
                        do {
                            System.out.print(" Enter option in number stated above > ");
                            userInput = scanner.nextLine();

                            if (userInput.equals("1")) {
                                System.out.print(" Enter new train name > ");
                                trainName = scanner.nextLine();
                                trainList.get(i).changeTrainName(trainName);
                            } else if (userInput.equals("#")){
                                break; // Exit the loop
                            }else {
                                System.out.println(" Invalid option. Please enter (1/#).");
                            }
                        } while (!userInput.equals("1") && !userInput.equals("#"));
                    }else if(userInput.equalsIgnoreCase("N")){
                        break;
                    }else{
                        System.out.println(" Invalid input. Please enter (Y/N).");
                        invalidInput = true;
                    }

                }while(invalidInput==true);
                invalidInput = false;
                updated = writeIntoFile("trainfile.txt", trainList, obj);
                
            }
            if (!found) {
                System.out.println(" Train is not found. Please search again.");
            } 
            if (updated){
                System.out.println("\n Train information has updated.\n");
            }
        } while (!found);  
                
             }           
        }

 
        
        do {
            found = false;
            
           
            // validation$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
            

            
               
                    do{
                        
                       
                        

                    }
                        
                    
    }