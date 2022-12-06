import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Fila do Banco");
        System.out.println("Informe o número de senhas que serão disponibilizadas:");

        int numbers = scan.nextInt();
        ManageAttendace manageAttendace = new ManageAttendace(numbers);

        operations(scan, manageAttendace);
    }

    private static void operations(Scanner scan, ManageAttendace manageAttendace) {
        System.out.println("Informe a operação que você deseja realizar:");
        System.out.println("1 - Chegada do cliente, \n2 - Verificar quem é o próximo a ser atendido, " +
                "\n3 - Atender um cliente, \n4 - Exibir as filas, \n5 - Finalizar o programa");

        int operation = scan.nextInt();

        switch (operation) {
            case 1:
                clientArrival(scan, manageAttendace);
                break;
            case 2:
                checkTheNextClient(scan, manageAttendace);
                break;
            case 3:
                serveClient(scan, manageAttendace);
                break;
            case 4:
                //4 - Exibir as filas (idoso e não idoso)
                System.out.println(manageAttendace.showQueries());
                operations(scan, manageAttendace);
                break;
            case 5:
                finishProgram(scan, manageAttendace);
                break;
            default:
                System.out.println("Opção inválida.\n");
                operations(scan, manageAttendace);
                break;
        }
    }
    private static void clientArrival(Scanner scan, ManageAttendace manageAttendace) {
        //1 - Chegada do cliente
        Client client = null;

        System.out.println("Informe o seu nome");
        String name = scan.next();

        System.out.println("Agora informe a sua idade:");
        int age = scan.nextInt();

        if(age >= 60 ) {
            client = new Elderly();
        } else {
            client = new Normal();
        }

        client.setName(name);
        client.setAge(age);

        manageAttendace.addClient(client);
        operations(scan, manageAttendace);
    }

    private static void checkTheNextClient(Scanner scan, ManageAttendace manageAttendace) {
        //2 - Verificar quem é o próximo a ser atendido
        if(!manageAttendace.isEmpty()) {
            System.out.println(manageAttendace.showNext());
        } else {
            System.out.println("Não há clientes aguardando na fila.\n");
        }
        operations(scan, manageAttendace);
    }

    private static void serveClient(Scanner scan, ManageAttendace manageAttendace) {
        //3 - Atender um cliente (ao atender o cliente, o nome e a idade dele deve ser apresentada

        if(!manageAttendace.isEmpty()) {
            manageAttendace.getNext();
        } else {
            System.out.println("Não há clientes para serem atendidos.\n");
        }

        operations(scan, manageAttendace);
    }

    private static void finishProgram(Scanner scan, ManageAttendace manageAttendace) {
        //5 - Finalizar o programa (que só poderá ser finalizado caso não tenha mais clientes aguardando)
        if(manageAttendace.isEmpty()) {
            System.exit(0);
        } else {
            System.out.println("Não pode terminar enquanto há clientes para atender.\n");
        }

        operations(scan, manageAttendace);
    }

}