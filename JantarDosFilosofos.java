import java.util.concurrent.Semaphore;

class Filosofo implements Runnable {
    private int id;
    private Semaphore[] garfos;

    public Filosofo(int id, Semaphore[] garfos) {
        this.id = id;
        this.garfos = garfos;
    }

    private void pensar() throws InterruptedException {
        System.out.println("Filósofo " + id + " está pensando.");
        Thread.sleep((long) (Math.random() * 3000));
    }

    private void comer() throws InterruptedException {
        System.out.println("Filósofo " + id + " está comendo.");
        Thread.sleep((long) (Math.random() * 3000));
    }

    private void pegarGarfos() throws InterruptedException {
        garfos[id].acquire();
        garfos[(id + 1) % garfos.length].acquire();
    }

    private void liberarGarfos() {
        garfos[id].release();
        garfos[(id + 1) % garfos.length].release();
    }

    @Override
    public void run() {
        try {
            while (true) {
                pensar();
                pegarGarfos();
                comer();
                liberarGarfos();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class JantarDosFilosofos {
    public static void main(String[] args) {
        int numFilosofos = 5;
        Semaphore[] garfos = new Semaphore[numFilosofos];

        for (int i = 0; i < numFilosofos; i++) {
            garfos[i] = new Semaphore(1);
        }

        Thread[] filosofos = new Thread[numFilosofos];

        for (int i = 0; i < numFilosofos; i++) {
            filosofos[i] = new Thread(new Filosofo(i, garfos));
            filosofos[i].start();
        }
    }
}
