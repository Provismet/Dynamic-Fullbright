Minor update to resolve long-standing issues regarding the sky.

## Additions
- The config menu now has an option to scale how much brightness is granted by the sky.
  - This option is enabled by default.
  - Disable this option if you want sky brightness to remain dependent on the time of day/weather.

## Changes
- Adjusted how entity brightness works to account for the new system.
- The option for maximum skylight is visible again.

## Bugfixes
- Raising the minimum skylight now works how you'd expect it to (when the new config option is enabled).

## Known Issues
- Skylight interacts strangely with foliage, doors, torches, and likely other non-standard blocks, causing them to be too dark or too bright.
  - This bug is fixed when using also using Sodium, so most users won't encounter it. 