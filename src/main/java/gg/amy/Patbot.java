package gg.amy;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.util.Arrays;
import java.util.List;

/**
 * @author samantha
 * @since 9/7/18
 */
@SuppressWarnings("unused")
public final class Patbot {
    private static final String CHANNEL = System.getenv("CHANNEL");
    private static final String TOKEN = System.getenv("TOKEN");
    private static final List<String> ALLOWED_IDS = Arrays.asList(System.getenv("ALLOWED_IDS").split(","));
    
    private Patbot() {
    }
    
    public static void main(final String[] args) throws LoginException, InterruptedException {
        new JDABuilder(AccountType.BOT)
                .setToken(TOKEN)
                .addEventListener(new ListenerAdapter() {
                    @Override
                    public void onGuildMessageReceived(final GuildMessageReceivedEvent event) {
                        if(event.getAuthor().isBot()) {
                            return;
                        }
                        if(event.getChannel().getId().equalsIgnoreCase(CHANNEL)) {
                            if(!event.getMessage().getContentRaw().equalsIgnoreCase("pat me")) {
                                if(!ALLOWED_IDS.contains(event.getAuthor().getId())) {
                                    event.getMessage().delete().queue();
                                }
                            } else {
                                event.getChannel().sendMessage(event.getAuthor().getAsMention() + " *pats*").queue();
                            }
                        }
                    }
                    
                    @Override
                    public void onGuildMessageUpdate(final GuildMessageUpdateEvent event) {
                        if(event.getAuthor().isBot()) {
                            return;
                        }
                        if(event.getChannel().getId().equalsIgnoreCase(CHANNEL)) {
                            if(!event.getMessage().getContentRaw().equalsIgnoreCase("pat me")) {
                                if(!ALLOWED_IDS.contains(event.getAuthor().getId())) {
                                    event.getMessage().delete().queue();
                                }
                            }
                        }
                    }
                })
                .build().awaitReady();
    }
}
