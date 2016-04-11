package shaffer.j;

public enum CommandOptions 
{
	HELP(1),
	DIR(2),
	CD(3),
	SHOW(4),
	EXIT(5);
	
	private final int value;
	
	private CommandOptions(int value)
	{
		this.value = value;
	}
}

