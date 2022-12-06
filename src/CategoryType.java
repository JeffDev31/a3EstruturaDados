public enum CategoryType {
    NORMAL("Normal"), PRIORITY("Prioridade");

    private String description;

    CategoryType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
