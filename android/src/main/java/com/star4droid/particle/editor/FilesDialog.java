package com.star4droid.particle.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.ray3k.gdxparticleeditor.Core;
import com.ray3k.stripe.PopTable;
import com.ray3k.stripe.ScrollFocusListener;
import java.util.ArrayList;

public class FilesDialog {
	String folder;
	Skin skin=Core.skin;
	Stage stage;
	int back = 0;
	ScrollPane scrollPane;
	ArrayList<Label> labels;
	String selected="";
	PopTable table;
	Table inner;
	private static String lastPath;
	
	public FilesDialog(){
		inner = new Table();
		table = new PopTable(skin.get(Window.WindowStyle.class));
		Label label = new Label("Choose File",skin,"bold");
		table.add(label).align(Align.center);
		scrollPane = new ScrollPane(inner,skin);
		scrollPane.setHeight(450);
		scrollPane.setWidth(450);
		scrollPane.setFlickScroll(true);
		labels = new ArrayList<>();
		TextButton button = new TextButton("Choose",Core.skin,"add");
		table.add(button);
		table.setKeepCenteredInWindow(true);
		table.setKeepSizedWithinStage(true);
		table.setHideOnUnfocus(true);
		button.addListener(new ClickListener(){
			@Override
            public void clicked(InputEvent event, float x, float y) {
                Core.saveRunnable.run();
				table.hide();
            }
		});
		//scrollPane.pack();
		//table.pack();
	}
	
	public static String getLastPath(){
		String temp = lastPath;
		lastPath = null;
		return temp;
	}
	
	public FilesDialog setPath(String folder){
		this.folder = folder;
		return this;
	}
	
	public FilesDialog setSkin(Skin skin){
		this.skin = skin;
		return this;
	}
	
	public FilesDialog setStage(Stage stage){
		this.stage = stage;
		scrollPane.addListener(new ScrollFocusListener(stage));
		return this;
	}
	
	public void pack(){
		scrollPane.pack();
		inner.pack();
		table.pack();
	}
	
	public PopTable get(){
		back = 0;
		getFiles(folder);
		return table;
	}
	
	private void getFiles(String path){
		getFiles(Gdx.files.absolute(path));
		inner.pack();
	}
	
	private void getFiles(FileHandle fh){
		table.clearChildren();
		labels.clear();
		selected = "";
		if(back>0){
			Label label = new Label("...",skin);
			labels.add(label);
			label.addListener(new ClickListener(){
				public void clicked (InputEvent event, float x, float y) {
					getFiles(fh.parent());
					back--;
				}
			});
			inner.add(label).width(250);
			inner.row();
		}
		Gdx.files.external("path.txt").writeString("\n".concat(fh.file().getAbsolutePath()),true);
		if(fh.exists()){
			for(FileHandle fileHandle:fh.list()){
				Label label = new Label(fh.nameWithoutExtension(),skin,"default");
				//if(selected.equals(fh.path())) label.getStyle().background.setColor(skin.getColor("selected-emitter"));
				labels.add(label);
				inner.add(label).width(250).minWidth(250);
				label.addListener(new ClickListener(){
					public void clicked (InputEvent event, float x, float y) {
						if(fileHandle.isDirectory()){
							back++;
							getFiles(fileHandle);
						} else {
							selected = fh.file().getAbsolutePath();
							//for(Label lb:labels)
								//lb.getStyle().background.setColor(skin.getColor("empty"));
							//label.getStyle().background.setColor(skin.getColor("selected-emitter"));
						}
					}
				});
			}
		}
	}
}