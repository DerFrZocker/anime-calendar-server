package de.derfrzocker.anime.calendar.server.notify.discord.impl.input;

import de.derfrzocker.anime.calendar.server.notify.api.NotificationAction;
import de.derfrzocker.anime.calendar.server.notify.discord.input.DiscordInputBuilder;
import java.util.ArrayList;
import java.util.List;
import net.dv8tion.jda.api.components.label.Label;
import net.dv8tion.jda.api.components.textinput.TextInput;
import net.dv8tion.jda.api.components.textinput.TextInputStyle;
import net.dv8tion.jda.api.modals.Modal;

public class JDADiscordInputBuilderImpl
        implements DiscordInputBuilder {

    private String title;
    private final List<TextFieldData> textFields = new ArrayList<>();

    @Override
    public DiscordInputBuilder setTitle(String title) {
        this.title = title;

        return this;
    }

    @Override
    public DiscordInputBuilder addTextField(String id, String label, int minLength, int maxLength) {
        this.textFields.add(new TextFieldData(id, label, minLength, maxLength));

        return this;
    }

    public Modal build(NotificationAction action) {
        Modal.Builder builder = Modal.create(action.id().raw(), this.title);

        List<Label> textInputs = new ArrayList<>();

        for (TextFieldData textField : this.textFields) {
            TextInput input = TextInput.create(textField.id(), TextInputStyle.SHORT)
                                       .setMinLength(textField.minLength())
                                       .setMaxLength(textField.maxLength())
                                       .build();

            textInputs.add(Label.of(textField.label(), input));
        }

        builder.addComponents(textInputs);

        return builder.build();
    }

    private record TextFieldData(String id, String label, int minLength, int maxLength) {

    }
}
