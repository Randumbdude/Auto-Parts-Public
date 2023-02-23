package autopartsclient.util.Key;

public interface IKeyBinding
{
	/**
	 * @return true if the user is actually pressing this key on their keyboard.
	 */
	public boolean isActallyPressed();
	
	/**
	 * Resets the pressed state to whether or not the user is actually pressing
	 * this key on their keyboard.
	 */
	public void resetPressedState();
}