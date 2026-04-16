package com.ray3k.gdxparticleeditor.widgets.tables;

import static com.ray3k.gdxparticleeditor.Core.*;
import static com.ray3k.gdxparticleeditor.Listeners.*;
import static com.ray3k.gdxparticleeditor.PresetActions.transition;
import static com.ray3k.gdxparticleeditor.Settings.*;
import static com.ray3k.gdxparticleeditor.undo.UndoManager.*;
import static com.ray3k.gdxparticleeditor.widgets.styles.Styles.tooltipBottomArrowStyle;
import static com.ray3k.gdxparticleeditor.widgets.styles.Styles.tooltipBottomRightArrowStyle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor.SystemCursor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.ray3k.gdxparticleeditor.Utils;
import com.ray3k.gdxparticleeditor.undo.UndoManager;
import com.ray3k.gdxparticleeditor.widgets.panels.EffectEmittersPanel;
import com.ray3k.gdxparticleeditor.widgets.panels.EmitterPropertiesPanel;
import com.ray3k.gdxparticleeditor.widgets.panels.PreviewPanel;
import com.ray3k.gdxparticleeditor.widgets.poptables.PopEditorSettings;

/** The widget layout for classic mode that mimics the original Particle Editor. */
public class ClassicTable extends Table {
  public static float size = 50;
  public static ClassicTable classicTable;
  private Table undoTable;

  public ClassicTable() {
    classicTable = this;
    if (preferences.getFloat(NAME_SCALE, DEFAULT_SCALE) != 1)
      Utils.updateViewportScale(preferences.getFloat(NAME_SCALE, DEFAULT_SCALE));
    pad(20).padBottom(5);

    var effectEmittersPanel = new EffectEmittersPanel();
    var previewPanel = new PreviewPanel();
    var emitterPropertiesPanel = new EmitterPropertiesPanel();

    var leftSplitPane = new SplitPane(effectEmittersPanel, previewPanel, true, skin);
    leftSplitPane.setSplitAmount(.5f);
    addSplitPaneVerticalSystemCursorListener(leftSplitPane);

    var horizontalSplitPane = new SplitPane(leftSplitPane, emitterPropertiesPanel, false, skin);
    add(horizontalSplitPane).grow();
    horizontalSplitPane.setSplitAmount(.4f);
    addSplitPaneHorizontalSystemCursorListener(horizontalSplitPane);

    row();
    var table = new Table();
    add(table).growX().padTop(5).padBottom(4);

    var label = new Label(version, skin);
    table.add(label);

    var textButton = new TextButton("-Update Available-", skin, "no-bg");
    textButton.setVisible(false);
    // table.add(textButton).spaceLeft(10);
    addHandListener(textButton);
    addTooltip(
        textButton, "Open browser to download page", Align.top, Align.top, tooltipBottomArrowStyle);
    onChange(
        textButton,
        () -> Gdx.net.openURI("https://github.com/libgdx/gdx-particle-editor/releases"));
    /*
    Utils.checkVersion(
        (String newVersion) -> {
          if (!versionRaw.equals(newVersion)) textButton.setVisible(true);
        });
    */
    var button = new Button(skin, "home");
    Utils.setButtonSize(button, size, size);
    table.add(button).expandX().right();
    addHandListener(button);
    addTooltip(
        button,
        "Return to the Home Screen",
        Align.top,
        Align.topLeft,
        tooltipBottomRightArrowStyle,
        false);
    onChange(button, () -> transition(this, new WelcomeTable(), Align.bottom));

    button = new Button(skin, "settings");
    Utils.setButtonSize(button, size, size);
    table.add(button);
    addHandListener(button);
    addTooltip(
        button,
        "Open the Editor Settings dialog",
        Align.top,
        Align.topLeft,
        tooltipBottomRightArrowStyle,
        false);
    onChange(
        button,
        () -> {
          Gdx.input.setInputProcessor(foregroundStage);
          Gdx.graphics.setSystemCursor(SystemCursor.Arrow);
          var pop = new PopEditorSettings();
          pop.show(foregroundStage);
        });

    undoTable = new Table();
    table.add(undoTable);

    refreshUndo();
  }

  public void refreshUndo() {
    undoTable.clearChildren();

    var button = new Button(skin, hasUndo() ? "undo-active" : "undo");
    Utils.setButtonSize(button, size, size);
    // button.setDisabled(!hasUndo());
    undoTable.add(button);
    addHandListener(button);
    if (hasUndo()) {
      var pop =
          addTooltip(
              button,
              "Undo ".concat(getUndoDescription()),
              Align.top,
              Align.topLeft,
              tooltipBottomRightArrowStyle,
              false);
      pop.setAttachOffsetX(8);
    }
    onChange(button, UndoManager::undo);

    button = new Button(skin, hasRedo() ? "redo-active" : "redo");
    Utils.setButtonSize(button, size, size);
    // button.setDisabled(!hasRedo());
    undoTable.add(button);
    addHandListener(button);
    if (hasRedo()) {
      var pop =
          addTooltip(
              button,
              "Redo ".concat(getRedoDescription()),
              Align.top,
              Align.topLeft,
              tooltipBottomRightArrowStyle,
              false);
      pop.setAttachOffsetX(8);
    }
    onChange(button, UndoManager::redo);
  }
}
