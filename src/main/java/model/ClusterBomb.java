package model;

public class ClusterBomb extends Bomb {
    private int numberOfMoves = 0;
    private final int numberOfBullet = 7;
    public ClusterBomb(Game game, int x, int y, double vx, double vy, double width, double height) {
        super(game, x, y, vx, vy, width, height);
    }

    @Override
    public boolean move() {
        numberOfMoves++;
        if (numberOfMoves == 60) {
            game.removeBomb(this);
            for (int i = 0; i < numberOfBullet; i++) {
                game.addBullet(new ClusterBullet(game, (int) getX(), (int) getY(), Math.cos(Math.toRadians(180 / numberOfBullet * i)) * 2, Math.sin(Math.toRadians(180 / numberOfBullet * i)) * 2, 20, 20));
            }
        }
        return super.move();
    }
}
