package ru.astrainteractive.astralibs.menu.one_page

import kotlinx.coroutines.*
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import ru.astrainteractive.astralibs.menu.menu.MenuSize
import ru.astrainteractive.astralibs.menu.holder.DefaultPlayerHolder
import ru.astrainteractive.astralibs.menu.holder.PlayerHolder
import ru.astrainteractive.astralibs.menu.menu.Menu
import ru.astrainteractive.astralibs.menu.multi_page.MultiPageMenu

class OnePageMenu(player: Player) : Menu() {
    override val playerHolder: PlayerHolder = DefaultPlayerHolder(player)
    val viewModel: OnePageViewModel by lazy {
        OnePageViewModel()
    }
    override var menuTitle: String = "One page menu"
    override val menuSize: MenuSize
        get() = MenuSize.M

    override fun onInventoryClicked(e: InventoryClickEvent) {
        e.isCancelled = true
        if (e.slot == viewModel.item.value.index)
            viewModel.onItemClicked()
        if (e.slot == viewModel.item.value.index)
            componentScope.launch(Dispatchers.IO) {
                MultiPageMenu(playerHolder.player).open()
            }
    }

    override fun onInventoryClose(it: InventoryCloseEvent) {
        viewModel.close()
    }

    override fun onCreated() {
        viewModel.item.collectOn {
            showInventoryButton(it)
        }
    }

    fun showInventoryButton(inventoryButton: InventoryButton) {
        inventoryButton.setInventoryButton()
    }


}