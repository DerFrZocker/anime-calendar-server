package de.derfrzocker.anime.calendar.server.notify.discord.impl.renderer;

import de.derfrzocker.anime.calendar.server.notify.discord.renderer.DiscordMessageBuilder;
import java.util.ArrayList;
import java.util.List;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

public class JDADiscordMessageBuilderImpl implements DiscordMessageBuilder {

    private String title;
    private String description;

    private final List<FieldData> fields = new ArrayList<>();
    private final List<ButtonData> buttons = new ArrayList<>();

    @Override
    public DiscordMessageBuilder setTitle(String title) {
        this.title = title;

        return this;
    }

    @Override
    public DiscordMessageBuilder setDescription(String description) {
        this.description = description;

        return this;
    }

    @Override
    public DiscordMessageBuilder addField(String title, String description) {
        this.fields.add(new FieldData(title, description));

        return this;
    }

    @Override
    public DiscordMessageBuilder addButton(String title, String id) {
        this.buttons.add(new ButtonData(title, id));

        return this;
    }

    public List<MessageCreateData> build() {
        List<EmbedBuilder> embeds = new ArrayList<>();

        embeds.add(new EmbedBuilder().setTitle(this.title).setDescription(this.description));

        for (int i = 0; i < this.fields.size(); i = i + 25) {
            EmbedBuilder embed;

            if (i == 0) {
                embed = embeds.getFirst();
            } else {
                embed = new EmbedBuilder();
                embed.setTitle("Continued");
                embeds.add(embed);
            }

            for (int j = i; j < this.fields.size() && (j - i) < 25; j++) {
                FieldData fieldData = this.fields.get(j);
                embed.addField(fieldData.title(), fieldData.description(), false);
            }
        }

        List<ActionRow> actionRows = new ArrayList<>();
        for (int i = 0; i < this.buttons.size(); i = i + 5) {
            List<Button> buttons = new ArrayList<>();
            for (int j = i; j < this.buttons.size() && (j - i) < 5; j++) {
                ButtonData buttonData = this.buttons.get(j);
                buttons.add(Button.of(ButtonStyle.PRIMARY, buttonData.id(), buttonData.title()));
            }

            actionRows.add(ActionRow.of(buttons));
        }

        List<MessageCreateBuilder> createBuilders = new ArrayList<>();
        for (int i = 0; i < embeds.size(); i = i + 10) {
            MessageCreateBuilder createBuilder = new MessageCreateBuilder();

            for (int j = i; j < embeds.size() && (j - i) < 10; j++) {
                createBuilder.addEmbeds(embeds.get(j).build());
            }

            createBuilders.add(createBuilder);
        }

        for (int i = 0; i < actionRows.size(); i = i + 5) {
            MessageCreateBuilder createBuilder;
            if (i == 0) {
                createBuilder = createBuilders.getLast();
            } else {
                createBuilder = new MessageCreateBuilder();
                createBuilders.add(createBuilder);
            }

            for (int j = i; j < actionRows.size() && (j - i) < 5; j++) {
                createBuilder.addComponents(actionRows.get(j));
            }
        }

        return createBuilders.stream().map(MessageCreateBuilder::build).toList();
    }

    private record FieldData(String title, String description) {

    }

    private record ButtonData(String title, String id) {

    }
}
