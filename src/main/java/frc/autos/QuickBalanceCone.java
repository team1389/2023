package frc.autos;

import java.util.HashMap;
import java.util.List;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.commands.FollowPathWithEvents;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.commands.AutoBalance;
import frc.commands.RunOuttakeCone;
import frc.commands.RunOuttakeCube;
import frc.commands.SetArmPosition;
import frc.commands.TimeArm;
import frc.robot.RobotMap.AutoConstants;
import frc.subsystems.Arm;
import frc.subsystems.Drivetrain;
import frc.subsystems.Intake;
import frc.subsystems.Arm.ArmPosition;

public class QuickBalanceCone extends SequentialCommandGroup{
   
    public QuickBalanceCone(Drivetrain drivetrain, Arm arm, Intake intake, HashMap<String, Command> hmm){
        addRequirements(drivetrain, arm, intake);

        PathPlannerTrajectory trajectory = PathPlanner.loadPath("Quick Balance Cone", new PathConstraints(
            AutoConstants.AUTO_MAX_METERS_PER_SEC-0.75, 
            AutoConstants.AUTO_MAX_MPSS-0.5));

        List<PathPlannerTrajectory> trajectories = PathPlanner.loadPathGroup("Quick Balance Cone", new PathConstraints(
            AutoConstants.AUTO_MAX_METERS_PER_SEC-0.75, 
            AutoConstants.AUTO_MAX_MPSS-0.5)
        );

        Command drivePath = drivetrain.followTrajectoryCommand(trajectories.get(0), true);
        Command drivePath2 = drivetrain.followTrajectoryCommand(trajectories.get(1), false);



        addCommands(
            new SetArmPosition(arm, ArmPosition.HighCone, false, 3.1),
            new RunOuttakeCone(intake, 0.5),
            drivePath,
            new SetArmPosition(arm, ArmPosition.StartingConfig, true),
            drivePath2,
            new AutoBalance(drivetrain)
        );
        
    }
}