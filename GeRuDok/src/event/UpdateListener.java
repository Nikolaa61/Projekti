package event;

import java.util.EventListener;

public interface UpdateListener extends EventListener{
	public void UpdatePerformed(UpdateEvent e);
}
