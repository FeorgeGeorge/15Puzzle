import java.util.Arrays;

public class Solver {
    int size;
    Puzzle[] queue;
    int[] distances; // MUST be updated with the queue;


    public Solver(Puzzle[] queue, int[] additionalDistance) {
        this.queue = queue;
        size = queue.length;
        distances = new int[size];
        for (int i = 0; i < size; i++) {
            distances[i] = queue[i].hammingDistance() + additionalDistance[i];
        }
        siftDown(0);
    }

    private void siftDown(int i) {
        while (2*i + 1 < size) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int j = left;
            if (right < size && distances[right] < distances[left]) {
                j = right;
            }
            if (distances[i] <= distances[j]) {
                break;
            }
            Puzzle c = queue[i];
            queue[i] = queue[j];
            queue[j] = c;

            int q = distances[i];
            distances[i] = distances[j];
            distances[j] = q;

            i = j;
        }
    }

    private void siftUp(int i) {
        while (distances[i] < distances[(i-1)/2]) {
            int child = (i-1)/2;

            Puzzle c = queue[i];
            queue[i] = queue[child];
            queue[child] = c;

            int q = distances[i];
            distances[i] = distances[child];
            distances[child] = q;

            i = child;
        }
    }

    public Puzzle extractMin() {
        Puzzle answer = queue[0];
        queue[0] = queue[size-1];
        size -= 1;
        siftDown(0);
        return answer;
    }

    private void expand(int index) {
        if (index >= queue.length) {
            queue = Arrays.copyOf(queue, 2*index);
        }
    }

    private void insert(Puzzle key, int distance) {
        size += 1;
        expand(size);
        queue[size-1] = key;
        distances[size-1] = distance + key.hammingDistance();
        siftUp(size-1);
    }


    public Puzzle[] solve() {
        Puzzle[] path = new Puzzle[10];

        int distance = 0;
        while (size > 0) {
            Puzzle current = extractMin();
            if (current.isSolved()) {
                return path;
            }
            if (distance >= path.length) {
                queue = Arrays.copyOf(queue, 2*distance);
            }
            path[distance] = current;
            distance += 1;
            Puzzle[] close = current.neighbours();
            for (Puzzle x : close) {
                if (x != null) {
                    insert(x, distance);
                }
            }
        }
        return new Puzzle[0];
    }
}
