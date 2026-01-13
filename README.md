# RadaeePDF SDK Classic for Android

<img src="https://www.radaeepdf.com/images/logo/logo-butterfly-only.png" style="width:100px;"> 

RadaeePDF SDK is a powerful, native PDF rendering and manipulation library for Android applications. Built from true native C++ code, it provides exceptional performance and a comprehensive set of features for working with PDF documents.

## About RadaeePDF

RadaeePDF SDK is designed to solve most developers' needs with regards to PDF rendering and manipulation. The SDK is trusted across industries worldwide including automotive, banking, publishing, healthcare, and more.

### Key Features

- **PDF ISO32000 Compliance** - Full support for the widely-used PDF format standard
- **High Performance** - True native code compiled from C++ sources for optimal speed
- **Annotations** - Create and manage text annotations, highlights, ink annotations, and more
- **Protection & Encryption** - Full AES256 cryptography for document security
- **Text Handling** - Search, extract, and highlight text with ease
- **Form Editing** - Create, read, and write PDF form fields (AcroForms)
- **Digital Signatures** - Sign and verify PDF documents with digital certificates
- **Multiple View Modes** - Single page, continuous scroll, and more
- **Night Mode** - Built-in dark mode support for better readability

## Quick Start - Run Demo

To quickly test the RadaeePDF SDK demo:

1. Open Android Studio
2. Click on **Clone Repository** (or File → New → Project from Version Control)
3. Paste the repository URL:
   ```
   https://github.com/RadaeePDF-Jugaad/RadaeePDF-Classic-Android.git
   ```
4. Click **Clone** and wait for the project to open
5. Open the `RDPDFReader` folder in the project structure (if needed)
6. Click the Play/Run button (▶) to run the demo in an emulator or connected device
## Installation

### Manual Installation

1. Download the RadaeePDF SDK library from Git Repository
   ```
   https://github.com/RadaeePDF-Jugaad/RadaeePDF-Classic-Android.git
   ```
2. Add the project ViewLib to your project

```gradle
dependencies {
    implementation project(':ViewLib')
}
```

## Getting Started

### Initialize the Library

Before using RadaeePDF, initialize the library with your license key:

1. Modify the 'Global.java' file in the 'com.radaee.pdf' package, to add your license key:
```java
public static String mSerial = "[YOUR-LICENSE-KEY]";
```

2. Add the following code to your 'Application' class to initialize and activate the library:
```java
import com.radaee.pdf.Global;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        
        // Initialize and activate RadaeePDF
        Global.Init(this);
    }
}
```

### Open and Display a PDF

#### Java

```java
import com.radaee.pdf.Document;
import com.radaee.reader.PDFLayoutView;
import com.radaee.view.ILayoutView;

public class MainActivity extends AppCompatActivity implements ILayoutView.PDFLayoutListener {
    private PDFLayoutView pdfView;
    private Document doc;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        pdfView = findViewById(R.id.pdf_view);
        
        // Open PDF document
        doc = new Document();

        int ret = doc.Open("/sdcard/sample.pdf", null);

        switch(ret){
            case 0:
                // Display in PDFGLLayoutView
                pdfView.PDFOpen(doc, MainActivity.this);
                break;
            case -1:
                // Show dialog to input password
                break;
            case -2:
                // Unknown encryption error
                break;
            case -3:
                // Damaged or invalid format
                break;
            case -10:
                // Access denied or invalid file path
                break;
            default:
                // Unknown error
                break;
        }
    }
    
    @Override
    protected void onDestroy() {
        if (pdfView != null) {
            pdfView.PDFClose();
        }
        if (doc != null && doc.IsOpened()) {
            doc.Close();
        }
        super.onDestroy();
    }
}
```

#### Kotlin

```kotlin
import com.radaee.pdf.Document
import com.radaee.reader.PDFLayoutView
import com.radaee.view.ILayoutView.PDFLayoutListener

class MainActivity : AppCompatActivity(), PDFLayoutListener {
    private lateinit var pdfView: PDFLayoutView
    private var doc: Document? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        pdfView = findViewById(R.id.pdf_view)
        
        // Open PDF document
        doc = Document()
        val ret = doc?.Open("/sdcard/sample.pdf", null): Int

        when (ret) {
            -1 -> {
                // Show dialog to input password
            }
            -2 -> {
                // Unknown encryption error
            }
            -3 -> {
                // Damaged or invalid format
            }
            -10 -> {
                // Access denied or invalid file path
            }
            0 -> {
                // Display in PDFGLLayoutView
                pdfView.PDFOpen(doc, this@MainActivity)
            }
            else -> {
                // Unknown error
            }
        }
    }
    
    override fun onDestroy() {
        pdfView?.PDFClose()

        if (doc != null && doc.IsOpened()) {
            doc.Close()
        }
        super.onDestroy()
    }
}
```

## Common Operations

### Get Page Count

```java
int pageCount = doc.GetPageCount();
```

### Navigate to a Specific Page

```java
pdfView.PDFGotoPage(5); // Go to page 5
```

### Zoom Control

```java
// Zoom in at screen point (x, y)
float currentZoom = 1.0f;
float newZoom = currentZoom * 1.2f;
PDFPos pos = pdfView.PDFGetPos(x, y);
pdfView.PDFSetZoom(x, y, pos, newZoom);

// Zoom out at screen point (x, y)
newZoom = currentZoom / 1.2f;
PDFPos pos = pdfView.PDFGetPos(x, y);
pdfView.PDFSetZoom(x, y, pos, newZoom);
```

### Enable Night Mode

```java
import com.radaee.pdf.Global;

Global.g_dark_mode = true;
pdfView.invalidate(); // Redraw the view
```

### Set View Mode

```java
// Vertical scroll mode
pdfView.PDFSetView(0);

// Horizontal scroll mode
pdfView.PDFSetView(1);

// Single page mode
pdfView.PDFSetView(3);

// Double page mode
pdfView.PDFSetView(4);


```

### Text Search (Professional License)

```java
// Start search
boolean match_case = false;
boolean match_whole_word = false;
pdfView.PDFFindStart("search term", match_case, match_whole_word);

// Find next/previous occurrence
int dir = 1; // 1 = next, -1 = previous
pdfView.PDFFind(dir);

// End search and reset
pdfView.PDFFindEnd();
```

### Text Highlighting (Professional License)

```java
// Highlight selected text
pdfView.PDFSetSelMarkup(0); // 0 = highlight

// Underline selected text
pdfView.PDFSetSelMarkup(1); // 1 = underline

// Strikeout selected text
pdfView.PDFSetSelMarkup(2); // 2 = strikeout

// Rod squiggly selected text
pdfView.PDFSetSelMarkup(4); // 4 = rod squiggly
```

### Add Annotations (Professional License)

```java
// Add a note annotation at point (x, y)
Page page = doc.GetPage(0); // Get the first page
if (page != null) {
    //Start object mode
    page.ObjsStart();

    PDFPos pos = pdfView.PDFGetPos(x, y);
    float[] pt = new float[2];
    pt[0] = pos.x;
    pt[1] = pos.y;
    page.AddAnnotText(pt);

    //Close page and release holding resource
    page.Close();
}

// Remove annotation
Page page = doc.GetPage(0); // Get the first page
if (page != null) {
    //Start object mode
    page.ObjsStart();
    Annotation annot = page.GetAnnot(index); // Get the annotation at index 0
    if(annot != null) {
        annot.RemoveFromPage();
    }

    //Close page and release holding resource
    page.Close();
}
pdfView.PDFRemoveAnnot();
```

### Save Document

```java
// Save changes to the same file
doc.Save();

// Save to a new file
boolean rem_sec = false; // Remove security information
doc.saveAs("/sdcard/newfile.pdf", rem_sec);
```

## License Levels

RadaeePDF offers different license levels with varying features:

Visit [https://www.radaeepdf.com/](https://www.radaeepdf.com/) for detailed licensing information.

## Documentation

For complete API documentation and advanced features, visit:
- [RadaeePDF Support Portal](https://support.radaeepdf.com/)
- [Wiki](https://github.com/RadaeePDF-Jugaad/RadaeePDF-Classic-Android/wiki)

## Support

For technical support and questions:
- Email: support@radaeepdf.com
- Website: [https://www.radaeepdf.com/](https://www.radaeepdf.com/)

## License

This SDK is commercial software. Please ensure you have a valid license before using it in production applications.

---

© 2026 RadaeePDF. All rights reserved.
