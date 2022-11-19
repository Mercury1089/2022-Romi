// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * This class represents the onboard IO of the Romi reference robot. This includes the pushbuttons
 * and LEDs.
 * <p>
 * DIO 0 - Button A (input) 
 * DIO 1 - Green LED (output)
 * DIO 2 - Red LED (output)
 * DIO 3 - Yellow LED (output)
 */
public class OnBoardIO extends SubsystemBase {

  private final DigitalInput m_buttonA = new DigitalInput(0);

  private final DigitalOutput m_greenLed = new DigitalOutput(1);
  private final DigitalOutput m_redLed = new DigitalOutput(2);
  private final DigitalOutput m_yellowLed = new DigitalOutput(3);

  /**
   * Constructor.
   *
   * @param dio1 Mode for DIO 1 (input = Button B, output = green LED)
   * @param dio2 Mode for DIO 2 (input = Button C, output = red LED)
   */
  public OnBoardIO() {
    m_greenLed.set(false);
    m_redLed.set(false);
    m_yellowLed.set(false);
  }

  /** Gets if the A button is pressed. */
  public boolean getButtonAPressed() {
    return m_buttonA.get();
  }
  /** Sets the green LED. */
  public void setGreenLed(boolean value) {
    m_greenLed.set(value);
  }

  /** Sets the red LED. */
  public void setRedLed(boolean value) {
    m_redLed.set(value);
  }

  /** Sets the yellow LED. */
  public void setYellowLed(boolean value) {
    m_yellowLed.set(value);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
