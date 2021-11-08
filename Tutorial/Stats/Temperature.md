# Temperature

Temperature is calculated by various variables, such as environmental temperature, and light level.

Some environmental factors that affects temperature:
- Biome temperature
- Daylight
- Light level
- If player is in water/rain
- If player is standing on ice/snow

Some physical factors that affects temperature:
- The player's armor
- If the player has [hypothermia](https://github.com/fishcute/ToughAsClient/blob/main/Tutorial/Status%20Effects/Hypothermia.md) effect
- If the player is [drenched](https://github.com/fishcute/ToughAsClient/blob/main/Tutorial/Status%20Effects/Drenched.md)

Unlike other stats, temperature has a goal value, called [immediate temperature](https://github.com/fishcute/ToughAsClient/blob/main/Tutorial/Stats/Immediate%20Temperature.md). This is the value the player's temperature will eventually reach.

When the player's temperature goes above 74°, the player will recieve the [hyperthermia](https://github.com/fishcute/ToughAsClient/blob/main/Tutorial/Status%20Effects/Hyperthermia.md) effect.
Along with if the player's temperature goes below 8°, the player will recieve [hypothermia](https://github.com/fishcute/ToughAsClient/blob/main/Tutorial/Status%20Effects/Hypothermia.md)

*The temperature bar on the bottom of the three primary bars, located to the bottom right of the screen*

![2021-11-08_16 11 48](https://user-images.githubusercontent.com/47741160/140834521-c6aaf823-b24e-492c-9e2b-28d13a0b01ea.png)
