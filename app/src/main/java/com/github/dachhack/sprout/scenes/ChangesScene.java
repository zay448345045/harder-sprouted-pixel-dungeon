package com.github.dachhack.sprout.scenes;

import com.github.dachhack.sprout.Chrome;
import com.github.dachhack.sprout.Messages.Messages;
import com.github.dachhack.sprout.ShatteredPixelDungeon;
import com.github.dachhack.sprout.ui.Archs;
import com.github.dachhack.sprout.ui.ExitButton;
import com.github.dachhack.sprout.ui.RenderedTextMultiline;
import com.github.dachhack.sprout.ui.ScrollPane;
import com.github.dachhack.sprout.ui.Window;
import com.watabou.noosa.Camera;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.ui.Component;

public class ChangesScene extends PixelScene {

    @Override
    public void create() {
        super.create();

        int w = Camera.main.width;
        int h = Camera.main.height;

        RenderedText title = PixelScene.renderText( Messages.get(this, "title"), 9 );
        title.hardlight(Window.TITLE_COLOR);
        title.x = (w - title.width()) / 2 ;
        title.y = 4;
        align(title);
        add(title);

        ExitButton btnExit = new ExitButton();
        btnExit.setPos( Camera.main.width - btnExit.width(), 0 );
        add( btnExit );

        RenderedTextMultiline text = renderMultiline(Messages.get(ChangesScene.class,"updatelogs"), 6 );


        int pw = w - 6;
        int ph = h - 20;


        NinePatch panel = Chrome.get(Chrome.Type.WINDOW);
        panel.size( pw, ph );
        panel.x = (w - pw) / 2;
        panel.y = title.y + title.height() + 2;
        add( panel );

        ScrollPane list = new ScrollPane( new Component() );
        add( list );

        Component content = list.content();
        content.clear();

        text.maxWidth((int) panel.innerWidth());

        content.add(text);

        content.setSize( panel.innerWidth(), text.height() );

        list.setRect(
                panel.x + panel.marginLeft(),
                panel.y + panel.marginTop(),
                panel.innerWidth(),
                panel.innerHeight());
        list.scrollTo(0, 0);

        Archs archs = new Archs();
        archs.setSize( Camera.main.width, Camera.main.height );
        addToBack( archs );

        fadeIn();
    }

    @Override
    protected void onBackPressed() {
        ShatteredPixelDungeon.switchNoFade(TitleScene.class);
    }
}
