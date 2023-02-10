package frc.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.subsystems.Intake;

public class RunOuttake extends CommandBase{
    Intake intake;

    public RunOuttake(Intake intake){
        this.intake = intake;
        addRequirements(intake);
    }

    @Override
    public void execute(){
        intake.runOuttake();
    }

    @Override
    public void end(boolean interrupted){
        intake.stop();
    }


}
