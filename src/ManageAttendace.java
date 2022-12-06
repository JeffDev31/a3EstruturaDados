import java.util.Arrays;

public class ManageAttendace {

    private int queuePosition;
    private Client[] queue;
    CategoryType[] categoryInTurnList = new CategoryType[]{
            CategoryType.PRIORITY,
            CategoryType.PRIORITY,
            CategoryType.NORMAL,
    };
    private int categoryInTurnCursor = 0;


    public ManageAttendace(int size) {
        this.queuePosition = -1;

        this.queue = new Client[size];
        for(int i = 0; i < this.queue.length; i++) {
            this.queue[i] = new Client();
        }
    }

    public int getCategoryInTurnCursor() {
        return categoryInTurnCursor;
    }

    public void setCategoryInTurnCursor(int categoryInTurnCursor) {
        this.categoryInTurnCursor = categoryInTurnCursor;
    }

    public boolean isEmpty() {
        //retorna true quando não há clientes para atendimento
        return this.queuePosition == -1;
    }

    public int numClients() {
        //retona o número de clientes aguardando atendimento
        if(this.isEmpty()) {
            return 0;
        }
        int clientsCounter = 0;
        for(int i = 0; i < this.queue.length; i++ ) {
            if(!this.queue[i].getName().isEmpty()) {
                clientsCounter++;
            }
        }
        return clientsCounter;
    }

    public int numElderlyClient() {
        //retona o número de clientes idosos aguardando atendimento
        if(this.isEmpty()) {
            return 0;
        }

        int elderlyClientsCounter = 0;

        for(int i = 0; i < this.queue.length; i++) {
            if(!this.queue[i].getName().isEmpty() && this.queue[i].isElderly()) {
                elderlyClientsCounter++;
            }
        }
        return elderlyClientsCounter;
    }

    public void addClient(Client cli) {
        //Insere um novo cliente na fila de atendimento
        if(this.queuePosition < this.queue.length -1
                && !cli.getName().isEmpty()
                && cli.getAge() != 0) {
            this.queue[++queuePosition] = cli;

            generateQueueNumber(cli);
        } else {
            System.out.println("A fila está cheia, novo cliente não foi inserido.\n");
        }
    }

    public Client showNext() {
        //No momento que um idoso deveria ser chamado, caso não tenha idoso, um não-idoso deverá ser chamado e vice-versa
        //retorna o próximo cliente a ser atendido, mas NÃO o remove da fila

        if(isEmpty()) {
            return null;
        }

        if(this.categoryInTurnList[this.categoryInTurnCursor].getDescription().
                equals(CategoryType.PRIORITY.getDescription())
                && elderlyQueueHasClients()) {

            return getElderlyQueue()[0];
        } else if(this.categoryInTurnList[this.categoryInTurnCursor].getDescription()
                .equals(CategoryType.NORMAL.getDescription())
                && normalQueueHasClients()) {

            return getNormalQueue()[0];
        } else if (this.categoryInTurnList[this.categoryInTurnCursor].getDescription()
                .equals(CategoryType.PRIORITY.getDescription())
                && normalQueueHasClients()) {

            return getNormalQueue()[0];
        }

        return null;
    }

    public Client getNext() {
        //No momento que um idoso deveria ser chamado, caso não tenha idoso, um não-idoso deverá ser chamado e vice-versa
        //retorna o próximo cliente para ser atendido e o remove da fila
        if(isEmpty()) {
            return null;
        }

        Client nextClient = null;

        if(this.categoryInTurnList[this.categoryInTurnCursor].getDescription().
                equals(CategoryType.PRIORITY.getDescription())
                && elderlyQueueHasClients()) {

            nextClient = getElderlyQueue()[0];
        } else if(this.categoryInTurnList[this.categoryInTurnCursor].getDescription()
                .equals(CategoryType.NORMAL.getDescription())
                && normalQueueHasClients()) {

            nextClient = getNormalQueue()[0];
        } else if (this.categoryInTurnList[this.categoryInTurnCursor].getDescription()
                .equals(CategoryType.PRIORITY.getDescription())
                && normalQueueHasClients()) {

            nextClient = getNormalQueue()[0];
        }

        Client[] updatedQueue = new Client[0];

        int updatedQueueCursor = 0;
        if(this.queue.length > 1) {
            updatedQueue = new Client[this.queue.length - 1];
            for(int i = 0; i < this.queue.length; i++) {
                assert nextClient != null;
                if(!this.queue[i].getName().equals(nextClient.getName())) {
                    updatedQueue[updatedQueueCursor] = this.queue[i];

                    updatedQueueCursor++;
                }
            }
        } else {
            this.queuePosition = -1;
        }

        this.queue = updatedQueue;

        if (nextClient != null) {
            System.out.println("Cliente " + nextClient.getName() + ":" +
                    nextClient.getAge() +" foi atendido(a)\n");
        }

        updatePriorityTurn();
        return nextClient;
    }

    public String showQueries() {
        //retorna uma string contendo os nomes de todos os clientes que estão aguardando atendimento.
        //o formato da string deve ser como o exemplo a seguir: idoso:nome1:idade1:-nome2:idade2
        //Quando a fila estiver vazia, exiba a palavra vazia como no exemplo: idoso:vazio;normal:nome1:idade1

        String showNormalQueue = this.normalQueueHasClients() ? Arrays.toString(getNormalQueue())
                .replace(",", "-")
                .replace(" ", "")
                .replace("[", "")
                .replace("]", "").trim() : "vazia";
        String showElderlyQueue = this.elderlyQueueHasClients() ? Arrays.toString(getElderlyQueue())
                .replace(",", "-")
                .replace(" ", "")
                .replace("[", "")
                .replace("]", "").trim() : "vazia";

        return "idoso:"+showElderlyQueue+";"+"normal:"+showNormalQueue;
    }

    public Client[] getNormalQueue() {
        boolean normalQueueHasClients = normalQueueHasClients();
        Client[] normalQueue = new Client[this.numClients() - this.numElderlyClient()];

        int normalQueuePosition = 0;
        if(normalQueueHasClients) {
            for (Client client : this.queue) {
                if (client.getClass().getSimpleName().equals("Normal")) {
                    normalQueue[normalQueuePosition] = client;
                    normalQueuePosition++;
                }
            }
            return normalQueue;
        }

        return new Client[0];
    }

    public Client[] getElderlyQueue() {
        boolean elderlyQueueHasClients = elderlyQueueHasClients();
        Client[] elderlyQueue = new Client[this.numElderlyClient()];

        int elderlyQueuePosition = 0;
        if(elderlyQueueHasClients) {
            for (Client client : this.queue) {
                if (client.getClass().getSimpleName().equals("Elderly")) {
                    elderlyQueue[elderlyQueuePosition] = client;
                    elderlyQueuePosition++;
                }
            }
            return elderlyQueue;
        }

        return new Client[0];
    }

    public boolean normalQueueHasClients() {
        if(this.queue.length > 0) {
            for(Client client : this.queue) {
                if(!client.isElderly() && !client.getName().isEmpty()) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean elderlyQueueHasClients() {
        if(this.queue.length > 0) {
            for(Client client : this.queue) {
                if(client.isElderly() && !client.getName().isEmpty()) {
                    return true;
                }
            }
        }

        return false;
    }

    public CategoryType updatePriorityTurn() {
        //ao chegar um idoso, o atendimento deste idoso deve ser priorizado
        //A cada dois atendimentos de idosos, um não idoso poderá ser atendido
        CategoryType nextClientType = this.categoryInTurnList[this.categoryInTurnCursor];

        this.categoryInTurnCursor++;
        if(categoryInTurnCursor >= 3) this.categoryInTurnCursor = 0;

        return nextClientType;
    }

    private static void generateQueueNumber(Client cli) {
        if(cli.getClass().getSimpleName().equals("Normal") && cli.getAge() != 0) {
            System.out.println("Senha normal gerada\n");
        } else if (cli.getClass().getSimpleName().equals("Elderly") && cli.getAge() != 0) {
            System.out.println("Senha preferencial gerada\n");
        }
    }

    //REFERÊNCIAS
    //Baelgung - Removing the First Element of an Array: https://www.baeldung.com/java-array-remove-first-element
    //Devmedia - Pilhas: Fundamentos e implementação da estrutura em Java: https://www.devmedia.com.br/pilhas-fundamentos-e-implementacao-da-estrutura-em-java/28241
}
