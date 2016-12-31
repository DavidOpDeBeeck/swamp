package de.daxu.swamp.scheduling.query.containerinstance;

import de.daxu.swamp.common.cqrs.EntityView;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table( name = "server_view" )
@SuppressWarnings( "unused" )
public class ServerView extends EntityView {

    @Column( name = "name" )
    private String name;

    @Column( name = "ip" )
    private String ip;

    private ServerView() {
    }

    private ServerView( String name, String ip ) {
        this.name = name;
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    public static class ServerViewBuilder {

        private String name;
        private String ip;

        public static ServerViewBuilder aServerView() {
            return new ServerViewBuilder();
        }

        public ServerViewBuilder withName( String name ) {
            this.name = name;
            return this;
        }

        public ServerViewBuilder withIp( String ip ) {
            this.ip = ip;
            return this;
        }

        public ServerView build() {
            return new ServerView( name, ip );
        }
    }
}
