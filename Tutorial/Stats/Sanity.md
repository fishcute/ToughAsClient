# Sanity

Sanity will decrease and increase depending on the environmental conditions of your player.

There are a few ways to decrease sanity:
- Staying underground
- Being above ground during a new moon
- Being in light level less than 3
- Being too cold or too hot
- Having health below 10
- Having hunger below 10
- Having [thirst](https://github.com/fishcute/ToughAsClient/blob/main/Tutorial/Stats/Thirst.md) below 22

And a few ways to increase sanity:
- Being above ground
- Being near a [comforting entity](https://github.com/fishcute/ToughAsClient/blob/main/Tutorial/Stats/Sanity.md#comforting-entities)
- Being in a light level above 5

When the player's sanity goes below 5, [insanity](https://github.com/fishcute/ToughAsClient/blob/main/Tutorial/Status%20Effects/Insanity.md) will be applied.

### The Sanity Bar

The sanity bar will display your current sanity. Unlike [stamina](https://github.com/fishcute/ToughAsClient/blob/main/Tutorial/Stats/Stamina.md), [temperature](https://github.com/fishcute/ToughAsClient/blob/main/Tutorial/Stats/Temperature.md), and [thirst](https://github.com/fishcute/ToughAsClient/blob/main/Tutorial/Stats/Thirst.md), the sanity bar is not always displayed. Sanity will not go down if the bar is not displayed.
Conditions to display sanity bar:
- Sanity is below 82
- Player's y level is below 30
- Player is underground
- Current moon phase is new moon during the night

*The sanity bar, in the bottom left of the screen*

![2021-11-08_14 40 34](https://user-images.githubusercontent.com/47741160/140811358-6c25b717-9e32-4794-ab62-0647540df833.png)

### Comforting Entities
Comforting entities are entity types that will increase the player's sanity if the player is near the entity type, and can see the entity type.
These entities consists of:
- Wolves
- Cats
- Foxes
- Villagers
- Wandering traders
- Iron golems
- Players

*Comfort particles around a wolf*

![2021-11-08_14 54 43](https://user-images.githubusercontent.com/47741160/140808809-7d35d5ab-106e-4516-b241-f6adc1976069.png)
