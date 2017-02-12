<img src="https://raw.github.com/DavidOpDeBeeck/swamp/master/swamp-ui/src/assets/img/favicon.png" width="64">

# Swamp

Swamp allows for a fast and easy deployment of all your projects. You can create and manage your own sandbox, create and deploy your own applications on a project basis. The main purpose of swamp is to manage a sandbox where you can deploy your projects.

## Development

### Configuration

#### REST API Database

```
> cd swamp-infrastructure/src/main/resources
> (vi|nano|notepad) application.yml (profiles: default)
```

#### Flyway Database

```
> cd .
> (vi|nano|notepad) gradle.properties
```

### Installation

```
> (./gradlew|gradlew.bat) flywayMigrate
> (./gradlew|gradlew.bat) :swamp-api:bootRun -Dversion=0.4-alpha
```

```
> cd swamp/swamp-ui
> gulp start
```

## Production

Swamp is currently not production ready.

## Versions

- [v0.1-alpha](https://github.com/DavidOpDeBeeck/swamp/releases/tag/v0.1-alpha)
- [v0.2-alpha](https://github.com/DavidOpDeBeeck/swamp/releases/tag/v0.2-alpha)
- [v0.3-alpha](https://github.com/DavidOpDeBeeck/swamp/releases/tag/v0.3-alpha)
- [v0.4-alpha](https://github.com/DavidOpDeBeeck/swamp/releases/tag/v0.4-alpha)

## Status Diagram

![status state diagram](https://raw.githubusercontent.com/DavidOpDeBeeck/swamp/master/models/status-state-diagram.jpg)

## UML

![deploy event uml](https://raw.githubusercontent.com/DavidOpDeBeeck/swamp/master/models/deploy-event-uml.png)