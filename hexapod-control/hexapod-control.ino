#include <Servo.h>

Servo left_prox_rot, left_prox_lift, left_prox_knee;
Servo left_med_rot, left_med_lift, left_med_knee;
Servo left_dist_rot, left_dist_lift, left_dist_knee;

Servo right_prox_rot, right_prox_lift, right_prox_knee;
Servo right_med_rot, right_med_lift, right_med_knee;
Servo right_dist_rot, right_dist_lift, right_dist_knee;

void setup() {
  left_prox_rot.attach(22);
  left_prox_lift.attach(23);
  left_prox_knee.attach(24);

  left_med_rot.attach(28);
  left_med_lift.attach(29);
  left_med_knee.attach(30);

  left_dist_rot.attach(34);
  left_dist_lift.attach(35);
  left_dist_knee.attach(36);

  right_prox_rot.attach(40);
  right_prox_lift.attach(41);
  right_prox_knee.attach(42);

  right_med_rot.attach(46);
  right_med_lift.attach(47);
  right_med_knee.attach(48);

  right_dist_rot.attach(50);
  right_dist_lift.attach(51);
  right_dist_knee.attach(53);


  delay(1000);

  left_prox_rot.write(90);
  left_prox_lift.write(90);
  left_prox_knee.write(90);

  left_med_rot.write(90);
  left_med_lift.write(90);
  left_med_knee.write(90);

  left_dist_rot.write(90);
  left_dist_lift.write(90);
  left_dist_knee.write(90);

  right_prox_rot.write(90);
  right_prox_lift.write(90);
  right_prox_knee.write(90);

  right_med_rot.write(90);
  right_med_lift.write(90);
  right_med_knee.write(90);

  right_dist_rot.write(90);
  right_dist_lift.write(90);
  right_dist_knee.write(90);


  delay(1000);
}

double a = 0.0;

void loop() {
//  if (true) return;
  int x1 = (int) (cos(-a) * 25) + 95;
  int y1 = (int) (sin(-a) * 35) + 55;
  int z1 = (int) (sin(-a) * 35) + 55;
  int x2 = (int) (cos(-a + 3.14159) * 25) + 95;
  int y2 = (int) (sin(-a + 3.14159) * 35) + 55;
  int z2 = (int) (sin(-a + 3.14159) * 35) + 55;
  int x3 = (int) (cos(-a) * 25) + 95;
  int y3 = (int) (sin(-a) * 35) + 65;
  int z3 = (int) (sin(-a) * 35) + 55;
  int x4 = (int) (cos(-a - 3.14159 / 3) * 25) + 80;
  int y4 = (int) (sin(-a - 3.14159 / 3) * 35) + 105;
  int z4 = (int) (sin(+a - 3.14159 / 3) * 35) + 105;
  int x5 = (int) (cos(-a - 3.14159) * 25) + 95;
  int y5 = (int) (sin(-a - 3.14159) * 35) + 105;
  int z5 = (int) (sin(-a - 3.14159) * 35) + 105;
  int x6 = (int) (cos(-a - 3.14159 / 3) * 25) + 105;
  int y6 = (int) (sin(-a - 3.14159 / 3) * 35) + 105;
  int z6 = (int) (sin(+a - 3.14159 / 3) * 35) + 105;

  left_prox_rot.write(x1);
  left_prox_lift.write(y1);
  left_prox_knee.write(z1);

  left_med_rot.write(x2);
  left_med_lift.write(y2);
  left_med_knee.write(z2);

  left_dist_rot.write(x3);
  left_dist_lift.write(y3);
  left_dist_knee.write(z3);

  right_prox_rot.write(x4);
  right_prox_lift.write(y4);
  right_prox_knee.write(z4);

  right_med_rot.write(x5);
  right_med_lift.write(y5);
  right_med_knee.write(z5);

  right_dist_rot.write(x6);
  right_dist_lift.write(y6);
  right_dist_knee.write(z6);

  delay(10); // step speed
  a += 0.015; // step angle 
}
