package com.egygames.apps.rssreader.article;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.egygames.apps.rssreader.BaseActivity;
import com.egygames.apps.rssreader.R;
import com.egygames.apps.rssreader.main.MainActivity;
import com.egygames.apps.rssreader.model.Article;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.img)
    ImageView imgeView;
    @BindView(R.id.title)
    TextView titleTextView;
    @BindView(R.id.date)
    TextView dateTextView;
    @BindView(R.id.section)
    TextView sectionTextView;
    @BindView(R.id.content)
    WebView contentWebView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        ButterKnife.bind(this);// BindView annotated fields and methods
        setArticleData();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void setArticleData() {
        Intent intent = getIntent();
        if (intent.hasExtra(MainActivity.ARTICLE_EXTRA)) {
            Article article = intent.getParcelableExtra(MainActivity.ARTICLE_EXTRA);

            if (article.getTitle() != null) {
                titleTextView.setText(article.getTitle());
            }
            if (article.getCreatedDate() != null) {
                dateTextView.setText(article.getCreatedDate());
            }
            if (article.getSectionName() != null) {
                sectionTextView.setText(article.getSectionName());
            }
            if (article.getDetails() != null) {
                loadHtml(article.getDetails());
            }

            if (article.getMainImg() != null) {
                Picasso.with(this)
                        .load(article.getMainImg())
                        .placeholder(R.drawable.default_img)
                        .into(imgeView);

            }
        }
    }

    private void loadHtml(String details) {
        String mime = "text/html";
        String encoding = "utf-8";
        contentWebView.loadDataWithBaseURL("file:///android_asset/", adjustTheContent(details), mime, encoding, null);
    }

    private String adjustTheContent(String content) {
        StringBuilder builder = new StringBuilder();
        builder.append("<html><head><meta name='viewport' content='width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, minimal-ui' />");
        builder.append("<style type='text/css'>  * {-webkit-user-select: none;}   @font-face {font-family: MyFont;src: url('file:///android_asset/DroidKufi-Regular.ttf')}body {font-family: MyFont;font-size:normal");
        builder.append("%;text-align: justify;}</style></head>");
        builder.append("<body dir='rtl'>");
        builder.append(content);
        builder.append("<br><br></body></html>");
        return builder.toString();
    }


}
