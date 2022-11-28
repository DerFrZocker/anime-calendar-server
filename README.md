Anime-Calendar
======

Anime-Calendar is an anime iCal service, to sync your airing anime with your calendar, such as google calendar, outlook,
iphone and many more.

For this Anime-Calendar will check your public anime list profile and creates calendar entries for when the animes air.
Those times are generally when they air in Japan (converted to your local time). If those animes air in germany
at [Crunchyroll.com](https://www.crunchyroll.com) then there is also the option to show the time when they release on
Crunchyroll.

## Usage:

To use it replace both `<username>` and `<anime-list>` in one off the urls below.

How you add an iCal feed to your calendar, depends on the calendar which you are using. Simple search
for `How to add iCal feed to <calendar>` and there should be guid for it.

### Supported anime list

Currently following anime lists are supported:<br>
***Coming soon***

### For Japan airing time:

`https://api.anime-calendar.com/v1/personal?user_provider=<anime-list>&user_id=<username>`

### For German airing time:

(If no german airing time is available it defaults to Japan airing time)<br>
`https://api.anime-calendar.com/v1/personal?user_provider=<anime-list>&user_id=<username>&region=de_de&airing_time=local`