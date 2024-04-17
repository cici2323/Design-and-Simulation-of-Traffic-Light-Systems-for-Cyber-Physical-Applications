
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrafficLight {
    private int STATE = 0;
    private int CURRENT_STATE = 5;  //四种状态, 0:由南向北 1:由北向南 2:由西向东 3:由东向西
    private int LINE_1 = 110;
    private int LINE_2 = 460;

    private int SPEED = 10;     //小车的速度
    private int LayerX = 500;   //小车位置
    private int LayerY = 100;   //小车位置

    private Frame frame = new Frame("Traffic Light");
    private final int TABALE_WIDTH = 600;   //画布宽度
    private final int TABALE_HEIGHT = 600;  //画布高度
    private final int REC_WIDTH = 150;      //灯板宽度
    private final int REC_HEIGHT = 50;      //灯板高度
    private final int C_SIZE = 30;          //灯的直径

    Button start = new Button("Start");
    Button simulate = new Button("Simulate");
    Button stop = new Button("Stop");
    Button exit = new Button("Exit");

    private Timer timer;    //灯变化的时间监听器
    private Timer CAR_timer;    //小车移动的时间监听器

    private class MyCanvas extends Canvas {
        @Override
        public void paint(Graphics g){
            //绘制线条
            g.drawLine(150, 0, 150, 600);
            g.drawLine(300, 50, 300, 550);
            g.drawLine(450, 0, 450, 600);
            g.drawLine(0, 150, 600, 150);
            g.drawLine(50, 300, 550, 300);
            g.drawLine(0, 450, 600, 450);

            // 绘制人行横道
            g.setColor(Color.WHITE); // 使用白色进行绘制
            int crosswalkStartY = 150;
            int crosswalkEndY = 450;
            int crosswalkX1 = 150; // 第一个人行横道的 X 坐标
            int crosswalkX2 = 400; // 第二个人行横道的 X 坐标
            int stripeWidth = 20; // 条纹的宽度为20像素
            int stripeLength = 50; // 每条条纹的长度为50像素
            int spaceBetweenStripes = 20; // 条纹开始点之间的距离是20像素

            // 绘制第一个人行横道的白色条纹
            for (int y = crosswalkStartY; y < crosswalkEndY; y += spaceBetweenStripes + stripeWidth) {
                g.fillRect(crosswalkX1, y, stripeLength, stripeWidth);
            }

            // 绘制第二个人行横道的白色条纹
            for (int y = crosswalkStartY; y < crosswalkEndY; y += spaceBetweenStripes + stripeWidth) {
                g.fillRect(crosswalkX2, y, stripeLength, stripeWidth);
            }
            //绘制黑色矩形框
            g.setColor(Color.BLACK);
            g.fillRect(150, 100, REC_WIDTH, REC_HEIGHT);
            g.fillRect(300, 450, REC_WIDTH, REC_HEIGHT);
            g.fillRect(100, 300, REC_HEIGHT, REC_WIDTH);
            g.fillRect(450, 150, REC_HEIGHT, REC_WIDTH);
            //小车移动超过UI界面的范围时，小车位置重新回到初始位置
            if (LayerY > 500 || LayerX < 0){
                LayerY = 0;
                LayerX = 500;
            }


            //灯的状态,0:由南向北行驶 1:由北向南行驶 2:由西向东行驶 3:由东向西行驶
            if (CURRENT_STATE == 0){
                g.fillOval(225, LayerX, 20, 20);//小车,这里只设置一辆直行的小车
                g.setColor(Color.GREEN);//绿灯
                //第一组灯
                g.fillOval(160, LINE_1, C_SIZE, C_SIZE); // light 1
                g.fillOval(210, LINE_1, C_SIZE, C_SIZE); // light 2
                g.fillOval(260, LINE_1, C_SIZE, C_SIZE); // light 3
                //四、七、十
                g.fillOval(LINE_2, 160, C_SIZE, C_SIZE); // light 4
                g.fillOval(405, LINE_2, C_SIZE, C_SIZE); // light 7
                g.fillOval(LINE_1, 405, C_SIZE, C_SIZE); // light 10

                g.setColor(Color.RED);//红灯
                //第二组灯
                g.fillOval(LINE_2, 210, C_SIZE, C_SIZE); // light 5
                g.fillOval(LINE_2, 260, C_SIZE, C_SIZE); // light 6
                //第三组灯
                g.fillOval(360, LINE_2, C_SIZE, C_SIZE); // light 8
                g.fillOval(310, LINE_2, C_SIZE, C_SIZE); // light 9

                //第四组灯
                g.fillOval(LINE_1, 360, C_SIZE, C_SIZE); // light 11
                g.fillOval(LINE_1, 310, C_SIZE, C_SIZE); // light 12

            }
            if (CURRENT_STATE == 1){
                g.fillOval(375, LayerY, 20, 20);//小车,这里只设置一辆直行的小车
                g.setColor(Color.GREEN);
                //第三组灯
                g.fillOval(310, LINE_2, C_SIZE, C_SIZE); // light 9
                g.fillOval(360, LINE_2, C_SIZE, C_SIZE); // light 8
                g.fillOval(405, LINE_2, C_SIZE, C_SIZE); // light 7

                g.fillOval(160, LINE_1, C_SIZE, C_SIZE); // light 1
                g.fillOval(LINE_2, 160, C_SIZE, C_SIZE); // light 4
                g.fillOval(LINE_1, 405, C_SIZE, C_SIZE); // light 10

                g.setColor(Color.RED);
                //第一组灯
                g.fillOval(210, LINE_1, C_SIZE, C_SIZE); // light 2
                g.fillOval(260, LINE_1, C_SIZE, C_SIZE); // light 3
                //第二组灯
                g.fillOval(LINE_2, 210, C_SIZE, C_SIZE); // light 5
                g.fillOval(LINE_2, 260, C_SIZE, C_SIZE); // light 6
                //第四组灯
                g.fillOval(LINE_1, 360, C_SIZE, C_SIZE); // light 11
                g.fillOval(LINE_1, 310, C_SIZE, C_SIZE); // light 12
            }
            if (CURRENT_STATE == 2){
                g.fillOval(LayerY, 225, 20, 20);//小车,这里只设置一辆直行的小车
                g.setColor(Color.GREEN);
                //第二组灯
                g.fillOval(LINE_2, 160, C_SIZE, C_SIZE); // light 4
                g.fillOval(LINE_2, 210, C_SIZE, C_SIZE); // light 5
                g.fillOval(LINE_2, 260, C_SIZE, C_SIZE); // light 6

                g.fillOval(160, LINE_1, C_SIZE, C_SIZE); // light 1
                g.fillOval(405, LINE_2, C_SIZE, C_SIZE); // light 7
                g.fillOval(LINE_1, 405, C_SIZE, C_SIZE); // light 10

                g.setColor(Color.RED);
                //第一组灯
                g.fillOval(210, LINE_1, C_SIZE, C_SIZE); // light 2
                g.fillOval(260, LINE_1, C_SIZE, C_SIZE); // light 3
                //第三组灯
                g.fillOval(360, LINE_2, C_SIZE, C_SIZE); // light 8
                g.fillOval(310, LINE_2, C_SIZE, C_SIZE); // light 9
                //第四组灯
                g.fillOval(LINE_1, 360, C_SIZE, C_SIZE); // light 11
                g.fillOval(LINE_1, 310, C_SIZE, C_SIZE); // light 12
            }
            if (CURRENT_STATE == 3){
                g.fillOval(LayerX, 375, 20, 20);//小车,这里只设置一辆直行的小车
                g.setColor(Color.GREEN);
                //第四组灯
                g.fillOval(LINE_1, 310, C_SIZE, C_SIZE); // light 12
                g.fillOval(LINE_1, 360, C_SIZE, C_SIZE); // light 11
                g.fillOval(LINE_1, 405, C_SIZE, C_SIZE); // light 10

                g.fillOval(160, LINE_1, C_SIZE, C_SIZE); // light 1
                g.fillOval(LINE_2, 160, C_SIZE, C_SIZE); // light 4
                g.fillOval(405, LINE_2, C_SIZE, C_SIZE); // light 7

                g.setColor(Color.RED);
                //第一组灯
                g.fillOval(210, LINE_1, C_SIZE, C_SIZE); // light 2
                g.fillOval(260, LINE_1, C_SIZE, C_SIZE); // light 3
                //第二组灯
                g.fillOval(LINE_2, 210, C_SIZE, C_SIZE); // light 5
                g.fillOval(LINE_2, 260, C_SIZE, C_SIZE); // light 6
                //第三组灯
                g.fillOval(360, LINE_2, C_SIZE, C_SIZE); // light 8
                g.fillOval(310, LINE_2, C_SIZE, C_SIZE); // light 9
            }
        }
    }
    MyCanvas drawArea = new MyCanvas();
    public void init() {
        //灯的时间监听器,每隔1000ms更新灯的状态
        ActionListener LIGHT_task = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                LayerY = 500;
                LayerX = 100;
                CURRENT_STATE = STATE % 4;
                drawArea.repaint();
                STATE += 1;
            }
        };
        //小车的时间监听器,每隔20ms更新小车的状态
        ActionListener CAR_task = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                LayerY += SPEED;
                LayerX -= SPEED;
                drawArea.repaint();
            }
        };
        //Start按键响应事件
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CURRENT_STATE = 0;
                drawArea.repaint();
            }
        });
        //Simulate按键响应事件
        simulate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer = new Timer(1000, LIGHT_task);//1000ms执行一次LIGHT_task任务
                timer.start();
                CAR_timer = new Timer(20, CAR_task);//20ms执行一次CAR_task任务
                CAR_timer.start();
                drawArea.repaint();
            }
        });
        //Stop按键响应事件
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();       //红绿灯停止闪烁
                CAR_timer.stop();   //小车停止移动
                drawArea.repaint();
            }
        });
        //Exit按键响应事件
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });
        Panel panel = new Panel();
        panel.add(start);
        panel.add(simulate);
        panel.add(stop);
        panel.add(exit);
        frame.add(panel, BorderLayout.SOUTH);
        drawArea.setPreferredSize(new Dimension(TABALE_WIDTH, TABALE_HEIGHT));
        frame.add(drawArea);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }
    public static void main(String[] args) {
        new TrafficLight().init();
    }
}
