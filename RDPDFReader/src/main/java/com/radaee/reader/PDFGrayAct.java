package com.radaee.reader;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.radaee.pdf.Document;
import com.radaee.pdf.Global;
import com.radaee.util.PDFAssetStream;

import com.radaee.viewlib.R;

public class PDFGrayAct extends Activity {
    private Document m_doc = new Document();
    private PDFAssetStream m_asset_stream = null;
    private Button btn_back;
    private Button btn_prev;
    private Button btn_next;
    private PDFGrayView m_view;

    private void onFail(String msg)//treat open failed.
    {
        m_doc.Close();
        m_doc = null;
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        finish();
    }
    private void ProcessOpenResult(int ret)
    {
        switch( ret )
        {
            case -1://need input password
                onFail(getString(R.string.failed_invalid_password));
                break;
            case -2://unknown encryption
                onFail(getString(R.string.failed_encryption));
                break;
            case -3://damaged or invalid format
                onFail(getString(R.string.failed_invalid_format));
                break;
            case -10://access denied or invalid file path
                onFail(getString(R.string.failed_invalid_path));
                break;
            case 0://succeeded, and continue
                m_view.PDFOpen(m_doc);
                break;
            default://unknown error
                onFail(getString(R.string.failed_unknown));
                break;
        }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Global.Init( this );
        Global.setDither16Grays(new int[] {36, 60, 79, 97, 113, 129, 143, 157, 171, 184, 197, 209, 221, 233, 245});
        /*
        //following codes to open PDF from http;
        m_http_stream = new PDFHttpStream();
        m_http_stream.open("https://www.radaeepdf.com/documentation/readeula/eula/eula.pdf");
        m_doc = new Document();
        int ret = m_doc.OpenStream(m_http_stream, null);
         */
        //following codes to open PDF from assets.
        setContentView(com.radaee.reader.R.layout.lay_gray);
        btn_back = findViewById(com.radaee.reader.R.id.btn_back);
        btn_prev = findViewById(com.radaee.reader.R.id.btn_prev);
        btn_next = findViewById(com.radaee.reader.R.id.btn_next);
        m_view = findViewById(com.radaee.reader.R.id.vw_gray);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_view.PDFPrevPage();
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_view.PDFNextPage();
            }
        });

        m_asset_stream = new PDFAssetStream();
        m_asset_stream.open(getAssets(), "dither.pdf");
        m_doc = new Document();
        int ret = m_doc.OpenStream(m_asset_stream, null);
        ProcessOpenResult(ret);
    }

    @Override
    protected void onDestroy() {
        m_view.PDFClose();
        super.onDestroy();
    }
}
