Anime-Calendar
======

Anime-Calendar is an anime iCal service, to sync your airing anime with your calendar, such as google calendar, outlook,
iphone and many more.

<p>

If the anime is licensed, it will show the time when it is available to stream, otherwise it will show the japan airing time (Converted to your local time).

## Usage:

As of now, the setup is a bit complicated, since there is no frontend for this service.

<p>

Before you can sync your animes to your calendar,
you need to let anime-calendar know which animes you watch / want to keep track of,
for this you need to first create a user by sending an empty `POST` request to following endpoint.
This will create and return a new user token, with which you can access the other endpoints.

`POST https://api.anime-calendar.com/v3/users`

Next you need to set which animes you watch, for this make a `PUT` request to the following REST Endpoint:

`PUT https://api.anime-calendar.com/v3/calendars/<user-token>/animes`

In the body you need to put a JsonArray with the ids of the animes you watch. You can find a list of animes and there ids [here](https://github.com/DerFrZocker/anime-calendar-server/wiki/Anime-List).
The format is as followed: 
```json
{
  "animeIds": [
    "W2JBQNSVRI",
    "VEXU7MT8S1"
  ]
}
```

After you send the `PUT` request you can add the following url to your calendar.

`https://api.anime-calendar.com/v3/ical/<user-token>`

How you add an iCal feed to your calendar, depends on the calendar which you are using. Simple search
for `How to add iCal feed to <calendar>` and there should be guid for it.
