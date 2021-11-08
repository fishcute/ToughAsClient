# Stamina

Stamina will go down as the player does various actions, and can limit the player's actions when low. Stamina naturally regenerates without player action.

There are a few ways to decrease stamina:
- Sprinting
- Jumping
- Breaking blocks
- Using a crossbow/bow
- Using a trident
- Blocking with a shield
- Swinging your hand

Stamina will regenerate over time without the requirement of player action. Holding shift will regenerate stamina faster. Having the strength effect will make the player regenerate stamina even faster. If the player has the speed effect applied, the player will not lose stamina by sprinting.

When the player reaches the [last stage](https://github.com/fishcute/ToughAsClient/blob/main/Tutorial/Stats/Stamina.md#stages), the player will recieve a [status message](https://github.com/fishcute/ToughAsClient/blob/main/Tutorial/Miscellaneous/Status%20Message.md) informing them they are too tired to continue running.

### Stages

There are three different stages in the stamina bar, as seen below:

![bar](https://user-images.githubusercontent.com/47741160/140820216-b091ba0d-a8aa-4140-8829-8683e374f8ba.png)

The first stage, or the rightmost/yellow stage, has the fastest regeration of all stages, and is the largest of all stages. In this stage, the player does not recieve any debuffs. This stage makes up 68% of the bar.

The second stage, or the middle/orange stage, has slower regeneration than the first stage. In this stage, the player cannot sprint. The player's FOV will also decrease. This stage makes up for 19% of the bar.

The third/last stage, or the leftmost/red stage, has the slowest regeneration of all the stages, and is the smallest of all stages. In this stage, the player is forced into sneaking, slowing the player down. The player's FOV will decrease by a larger amount. This stage makes up for 13% of the bar.

*The stamina bar in the middle of the three primary bars by default, located to the right bottom of the screen*

![2021-11-08_16 11 48](https://user-images.githubusercontent.com/47741160/140820056-e3c0c8f3-23c2-472c-a428-6ae55778cf1b.png)
