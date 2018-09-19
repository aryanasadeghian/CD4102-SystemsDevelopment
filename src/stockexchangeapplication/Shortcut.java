
package stockexchangeapplication;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class Shortcut {
    private StringProperty description;
    private StringProperty shortcut;
    
    public Shortcut(String descriptionIn, String shortcutIn) {
        this.description = new SimpleStringProperty(descriptionIn);
        this.shortcut = new SimpleStringProperty(shortcutIn);
    }
    
    public String getDescription() {
        return this.description.get();
    }
    
    public String getShortcut() {
        return this.shortcut.get();
    }
    
}
