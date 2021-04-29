package com.roomedia.dakku.ui.editor.menu

import android.view.View
import com.roomedia.dakku.R

class MainMenuBar(private val menuHandlersManager: MenuBarManager) {
    fun onAdd(view: View) {
        menuHandlersManager.selectMenu(view.id)
        menuHandlersManager.showSubMenu(R.id.addMenu)
    }

    fun onUndo() {
        TODO("not yet implemented")
    }

    fun onRedo() {
        TODO("not yet implemented")
    }

    fun onDelete(view: View) {
        menuHandlersManager.selectMenu(view.id)
        menuHandlersManager.hideAllSubMenuHandlers()
        menuHandlersManager.deleteSelectedSticker()
    }

    fun onLayer(view: View) {
        menuHandlersManager.selectMenu(view.id)
        menuHandlersManager.setSeekBarListener(LayerListener::class)
    }

    fun onDuplicate(view: View) {
        menuHandlersManager.selectMenu(view.id)
        TODO("not yet implemented")
    }
}
