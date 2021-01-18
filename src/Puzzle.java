public class Puzzle {
    final int width;
    final int height;
    Piece[][] content;
    int xB; // blank's coordinates
    int yB;

    public  Puzzle(Piece[][] content) { // generates a custom puzzle;
        // if width < 1 throws exception;
        this.width = content[0].length;
        this.height = content.length;
        this.content = content;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (content[i][j].isBlank()) {
                    yB = i;
                    xB = j;
                    // there might not be a blank;
                }
            }
        }
    }

    public Puzzle(final int width, final int height) {
        // generates a solved position for the puzzle;
        this.width = width;
        this.height = height;
        Piece[][] content = new Piece[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i*width + j == height * width - 1) {
                    content[i][j] = new Piece(-1);
                }
                else {
                    content[i][j] = new Piece(i*width + j + 1);
                }
            }
        }
        this.content = content;
    }

    public Piece getPiece(int x, int y) {
        return content[y][x];
    }

    public void transpose(int x, int y, int dx, int dy) { // dx = 0 / 1; dy = 0 / 1;
        boolean check = (dx == 0 && dy == 0 || dx == 1 && dy == 1 || dx == -1 && dy == -1);
        int newx = x + dx;
        int newy = y + dy;
        if (!(getPiece(x,y).isBlank() || getPiece(dx, dy).isBlank()) || check
            || newx < 0 || newx >= width || newy < 0 || newy >= height){
            throw new IllegalArgumentException("This move is illegal!");
        }

        Piece c = getPiece(x,y);
        content[y][x] = getPiece(newx, newy);
        content[newy][newx] = c;
    }

    public Piece getPiece(int x) {
        return content[x / width][x % width];
    }

    public String toString() {
        int size = width*height;
        int length = 0;
        while (size > 0) {
            size /= 10;
            length++;
        }
        StringBuilder answer = new StringBuilder();
        for (int i = 0; i < height; i++) {
            StringBuilder line = new StringBuilder();
            for (int j = 0; j < width; j++) {
                int x = getPiece(i,j).getValue();
                String s;
                if (x == -1) {
                    s = " ".repeat(length);
                }
                else {
                    s = Integer.toString(x);
                }
                int currentLength = s.length();
                line.append(x);
                line.append(" ".repeat(length- currentLength));
            }
            answer.append(line);
            answer.append("\n");
        }
        return answer.toString();
    }

    public boolean equals(Puzzle another) {
        return width == another.width
                && height == another.height
                && content == another.content;
    }

    public int hammingDistance() {
        int answer = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (this.getPiece(x,y).getValue() != y * width + x + 1  && y * width + x != width * height - 1) {
                    answer += 1;
                }
            }
        }
        return answer;
    }

    public boolean isSolved() {
        return this.equals(new Puzzle(width, height));
    }

    public Puzzle[] neighbours() {
        Puzzle[] answer = new Puzzle[4];
        int counter = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0 || i == 1 && j == 1 || i == -1 && j == -1) {
                    continue;
                }
                Puzzle copy = new Puzzle(content);
                copy.transpose(xB, yB, i, j);
                answer[counter] = copy;
                counter++;
            }
        }
        return answer;
    }
}
