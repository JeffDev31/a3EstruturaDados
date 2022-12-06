public class Client {

    private String name = "";
    private int age = 0;

    public boolean isElderly() {
        if(this.age != 0) return this.age >= 60;

        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name.length() >= 5) {
            this.name = name;
        } else {
            System.out.println("O nome do cliente deve possuir pelo menos 5 caracteres\n");
        }
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if(age >= 16) {
            if(age <= 140) {
                this.age = age;
            } else {
                System.out.println("O sistema não aceita clientes com idade superior a 140 anos\n");
            }
        } else {
            System.out.println("A idade mínima para ser cliente do banco é de 16 anos.\n");
        }
    }

    @Override
    public String toString() {
        return this.getName()+":"+this.getAge();
    }
}
