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

    public static class Builder {

        private String name;
        private String ip;

        public static Builder aServerView() {
            return new Builder();
        }

        public Builder withName( String name ) {
            this.name = name;
            return this;
        }

        public Builder withIp( String ip ) {
            this.ip = ip;
            return this;
        }

        public ServerView build() {
            return new ServerView( name, ip );
        }
    }
}
