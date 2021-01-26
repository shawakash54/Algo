public class HTree {
    public static void main(String[] args) {
        drawHTree(0,0,8,2);
    }
    public static void drawLine(double x1, double y1, double x2, double y2) {
        System.out.println("Line is drawn by the point: " + "(" + x1 + "," + y1 + ") " + "and " + "(" + x2 + "," + y2 + ")");
    }

    public static void drawHTree(double x, double y, double length, int depth) {
        // Base condition
        if(depth == 0)
            return;
        
        double delta = length/2;
        // Draw the present H Tree
        // 1 horizontal line, 2 vertical lines
        drawLine(x - delta, y, x + delta, y);
        drawLine(x - delta, y - delta, x - delta, y + delta);
        drawLine(x + delta, y - delta, x + delta, y + delta);
        
        length = length/Math.sqrt(2);
        
        // Recursively call to draw the next 4 H's with length reduced by sqrt(2)
        // also decrease depth by 1
        drawHTree(x-delta, y+delta, length, depth-1);
        drawHTree(x-delta, y-delta, length, depth-1);
        drawHTree(x+delta, y+delta, length, depth-1);
        drawHTree(x+delta, y-delta, length, depth-1);
    }
}
