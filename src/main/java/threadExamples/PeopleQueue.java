package threadExamples;

class PeopleQueue extends Thread    {
    private String[] names;

    PeopleQueue(String... names) {
        this.names = names;
    }

    @Override
    public void run() {
        for (int i = 0; i < names.length; i++) {
            System.out.println("Обработаны документы: " + names[i]);
            try {
                sleep(500); // Задержка в 0.5 сек
            } catch (Exception e) {}
        }
    }
}

 class HR    {
    public static void main(String[] args) {
        // Создаем две очереди
        PeopleQueue queue1 = new PeopleQueue("Иван","Сергей","Николай","Фердинанд","Василий");
        PeopleQueue queue2 = new PeopleQueue("валерий","Людмила","Алиса","Карина","Ольга");

        System.out.println("Начали!");
        queue1.start();
        queue2.start();
    }
}