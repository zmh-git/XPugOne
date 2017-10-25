package core.commands;

import java.util.ArrayList;

import core.Constants;
import core.entities.QueueManager;
import core.exceptions.BadArgumentsException;
import core.exceptions.DoesNotExistException;
import core.exceptions.InvalidUseException;
import core.util.Functions;
import net.dv8tion.jda.core.entities.Member;

public class CmdRemove extends Command {

	public CmdRemove() {
		this.helpMsg = Constants.REMOVE_HELP;
		this.successMsg = Constants.REMOVE_SUCCESS;
		this.description = Constants.REMOVE_DESC;
		this.adminRequired = true;
		this.name = "remove";
	}

	@Override
	public void execCommand(QueueManager qm, Member member, ArrayList<String> args) {
		try {
			if (args.size() > 0) {
				String name = args.get(0);
				args.remove(0);
				
				if (args.size() == 0) {
					qm.remove(name);
				} else {
					for (String s : args) {
						try {
							qm.remove(name, Integer.valueOf(s));
						} catch (NumberFormatException ex) {
							qm.remove(name, s);
						}
					}
				}

			} else {
				throw new BadArgumentsException();
			}
			qm.updateTopic();
			this.response = Functions.createMessage(String.format("Player removed from queue"), qm.getHeader(), true);
			System.out.println(successMsg);
		} catch (BadArgumentsException | DoesNotExistException | InvalidUseException ex) {
			this.response = Functions.createMessage("Error!", ex.getMessage(), false);
		}
	}

}