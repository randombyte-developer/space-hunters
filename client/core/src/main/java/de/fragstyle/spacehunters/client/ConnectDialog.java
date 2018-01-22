package de.fragstyle.spacehunters.client;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public abstract class ConnectDialog extends Dialog {

  private TextField usernameInput;
  private TextField hostnameInput;
  private Label statusLabel;

  public ConnectDialog(String title, Skin skin, String defaultUsername, String defaultHostname) {
    super(title, skin);

    text("Spielername: ");
    usernameInput = new TextField(defaultUsername, skin);
    getContentTable().add(usernameInput);

    text("Server-Adresse: ");
    hostnameInput = new TextField(defaultHostname, skin);
    getContentTable().add(hostnameInput);

    statusLabel = new Label("", skin);
    text(statusLabel);

    button("Verbinden");
  }

  @Override
  protected void result(Object object) {
    cancel(); // This dialog has to be closed manually
    gotInput(this, usernameInput.getText(), hostnameInput.getText());
  }

  protected abstract void gotInput(ConnectDialog dialog, String username, String hostname);

  public void setStatus(String status) {
    statusLabel.setText(status);
  }
}
