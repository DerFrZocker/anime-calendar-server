Anime-Calendar
======

Anime-Calendar is an anime iCal service, to sync your airing anime with your calendar, such as Google Calendar, Outlook,
iPhone and many more.

<p>

If the anime is licensed, it will show the time when it is available to stream, otherwise it will show the japan airing
time (Converted to your local time).

## Usage:

You can use this service in the following ways:

---

### Using MyAnimeList

##### Sync with your MyAnimeList account

If you use [MyAnimeList](https://myanimelist.net/)
and have set your watchlist to `public`, you can access the following endpoint
to generate an iCal calendar containing all the animes from your watchlist:

```http request
https://api.anime-calendar.com/v3/ical/myanimelist/<myanimelist-username>
```

##### Sync based on MyAnimeList anime IDs

Alternatively, you can specify individual anime using the `ani` and/or `animeId` query parameters.
This method generates a calendar based only on the anime IDs you provide:

```http request
https://api.anime-calendar.com/v3/ical/myanimelist?ani=<myanimelist-anime-id>&ani=<myanimelist-anime-id>&animeId=<myanimelist-anime-id>&animeId=<myanimelist-anime-id>
```

*Note: Both ani and animeId refer to MyAnimeList anime IDs. You can mix and match them as needed.*

---

### Using Anime-Calendar Anime IDs

You can also generate a calendar using Anime-Calendar's own internal anime IDs, independent of MyAnimeList.
You can find a list of Anime-Calendars anime IDs [here](https://github.com/DerFrZocker/anime-calendar-server/wiki/Anime-List).

<p>

Use the following endpoint with one or more `ani` and/or `animeId` query parameters:

```http request
https://api.anime-calendar.com/v3/ical?ani=<anime-calendar-anime-id>&ani=<anime-calendar-anime-id>&animeId=<anime-calendar-anime-id>
```

*Note: Both ani and animeId refer to Anime-Calendar anime IDs. You can mix and match them as needed.*

---

### Using the Anime Calendar User and Calendar System

*Feature in progress.*

---

### iCal Options

You can customize your calendar using optional query parameters on the iCal endpoint:

| Query Parameter | Possible Values | Default      | Description                                                                                                                                                                                                                                                                                                                                                              |
|-----------------|-----------------|--------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| streamType      | `org`, `sub`    | `sub`, `org` | You can specify multiple `streamType` parameters to set the priority order. The first one provided has the highest priority. If no time is available for that type, the next one is used. For example, setting `streamType=sub&streamType=org` means the calendar will use the subtitled release time if available, and fall back to the original airing time otherwise. |
