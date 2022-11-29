// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.DriveDistance;
import frc.robot.commands.DriveTime;
import frc.robot.commands.TurnDegrees;
import frc.robot.commands.TurnTime;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.OnBoardIO;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Drivetrain m_drivetrain = new Drivetrain();
  private final OnBoardIO m_onboardIO = new OnBoardIO();

  // Assumes a gamepad plugged into channnel 0
  private final Joystick m_controller = new Joystick(0);
  private final JoystickButton m_buttonA = new JoystickButton(m_controller, 1);
  private final JoystickButton m_buttonB = new JoystickButton(m_controller, 2);
  private final JoystickButton m_buttonX = new JoystickButton(m_controller, 3);
  private final JoystickButton m_buttonY = new JoystickButton(m_controller, 4);

  // Create SmartDashboard chooser for autonomous routines
  private final SendableChooser<Command> m_chooser = new SendableChooser<>();


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

     // Drivetrain default command
    // Use arcade drive. This will run unless another command uses the drivetrain.
    // Check which JS axis should be used to speed and rotate
    m_drivetrain.setDefaultCommand(
      new ArcadeDrive(
        m_drivetrain,
        () -> -m_controller.getRawAxis(1), () -> m_controller.getRawAxis(4)
      )
    );


    // JS Buttons

    // JS Button Example 1 - Tying a simple command to a button
    // What is a command? Let's look at TurnTime.java...
    m_buttonY.whenPressed(new TurnTime(0.5, 3, m_drivetrain));


    // JS Button Example 2 - Using whenPressed and whenReleased to trigger commands
    m_buttonA
      .whenPressed(new RunCommand(() -> m_onboardIO.setGreenLed(true), m_onboardIO))
      .whenReleased(new RunCommand(() -> m_onboardIO.setGreenLed(false), m_onboardIO));

    // JS Button Example 3 - Using toggleWhenPressed with a StartEndCommend instead
    m_buttonB.toggleWhenPressed(new StartEndCommand(
                                      () -> m_onboardIO.setRedLed(true),  // Runs when the command starts
                                      () -> m_onboardIO.setRedLed(false), // Runs when the command ends
                                      m_onboardIO));

    // JS Button Example 4 - Sequential Commands
    // Command Groups combine multiple commands into one big one
    // Sequential Command Groups run each command in sequence
    
    m_buttonX.whenPressed(
      new SequentialCommandGroup(
        new DriveTime(-0.6, 2.0, m_drivetrain),
        new TurnTime(-0.5, 1.3, m_drivetrain),
        new DriveTime(-0.6, 2.0, m_drivetrain),
        new TurnTime(0.5, 1.3, m_drivetrain)
      )
    );


    // Trigger Example
    // A trigger works like a joystick button, but with any boolean.
    // Here we check the status of the digital IO button A on the Romi
    Trigger onboardButtonA = new Trigger(() -> m_onboardIO.getButtonAPressed());
    onboardButtonA
        .whenActive(new RunCommand(() -> m_onboardIO.setYellowLed(true), m_onboardIO))
        .whenInactive(new RunCommand(() -> m_onboardIO.setYellowLed(false), m_onboardIO));


    // Setup SmartDashboard options

    // Setup SmartDashboard options

    m_chooser.setDefaultOption("Auto Routine Time", 
      new SequentialCommandGroup(
        new DriveTime(-0.6, 2.0, m_drivetrain),
        new TurnTime(-0.5, 1.3, m_drivetrain),
        new DriveTime(-0.6, 2.0, m_drivetrain),
        new TurnTime(0.5, 1.3, m_drivetrain)
      )
    );

    m_chooser.addOption("Auto Routine Distance",
      new SequentialCommandGroup(
        new DriveDistance(-0.5, 10, m_drivetrain),
        new TurnDegrees(-0.5, 90, m_drivetrain),
        new DriveDistance(-0.5, 10, m_drivetrain),
        new TurnDegrees(0.5, 90, m_drivetrain)
      )
    );

    SmartDashboard.putData(m_chooser);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {

    // The following just prints a message indicating that no auton has been created.
    // PART FOUR
    // - See if you can change this to return the the Sequential Command added to buttonX
    // - Run in Autonomous mode to see if it worked
    //return new PrintCommand("NO AUTON?!");

    //return new SequentialCommandGroup(
    //    new DriveTime(-0.6, 2.0, m_drivetrain),
    //    new TurnTime(-0.5, 1.3, m_drivetrain),
    //    new DriveTime(-0.6, 2.0, m_drivetrain),
    //    new TurnTime(0.5, 1.3, m_drivetrain)
    //  );

    // PART FIVE
    // - Comment out all code above this line.
    // - Uncomment the return statement below to get the selected auton from ShuffleBoard

    return m_chooser.getSelected();
  }
}
