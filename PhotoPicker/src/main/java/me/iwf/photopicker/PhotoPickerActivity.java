package me.iwf.photopicker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import me.iwf.photopicker.entity.Photo;
import me.iwf.photopicker.event.OnItemCheckListener;
import me.iwf.photopicker.fragment.ImagePagerFragment;
import me.iwf.photopicker.fragment.PhotoPickerFragment;
import me.iwf.photopicker.utils.BitmapUtil;
import me.iwf.photopicker.widget.Titlebar;

import static android.widget.Toast.LENGTH_LONG;
import static me.iwf.photopicker.PhotoPicker.DEFAULT_COLUMN_NUMBER;
import static me.iwf.photopicker.PhotoPicker.DEFAULT_MAX_COUNT;
import static me.iwf.photopicker.PhotoPicker.EXTRA_GRID_COLUMN;
import static me.iwf.photopicker.PhotoPicker.EXTRA_MAX_COUNT;
import static me.iwf.photopicker.PhotoPicker.EXTRA_ORIGINAL_PHOTOS;
import static me.iwf.photopicker.PhotoPicker.EXTRA_PREVIEW_ENABLED;
import static me.iwf.photopicker.PhotoPicker.EXTRA_SHOW_CAMERA;
import static me.iwf.photopicker.PhotoPicker.EXTRA_SHOW_GIF;
import static me.iwf.photopicker.PhotoPicker.KEY_SELECTED_PHOTOS;

public class PhotoPickerActivity extends AppCompatActivity {

  private PhotoPickerFragment pickerFragment;
  private ImagePagerFragment imagePagerFragment;
  //private MenuItem menuDoneItem;

  private int maxCount = DEFAULT_MAX_COUNT;
  private ProgressDialog dialog;

  /** to prevent multiple calls to inflate menu */
 // private boolean menuIsInflated = false;

  private boolean showGif = false;
  private int columnNumber = DEFAULT_COLUMN_NUMBER;
  private ArrayList<String> originalPhotos = null;

  private Titlebar titlebar;
  private ArrayList<String> intentList = new ArrayList<String>();


  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    boolean showCamera      = getIntent().getBooleanExtra(EXTRA_SHOW_CAMERA, true);
    boolean showGif         = getIntent().getBooleanExtra(EXTRA_SHOW_GIF, false);
    boolean previewEnabled  = getIntent().getBooleanExtra(EXTRA_PREVIEW_ENABLED, true);

    setShowGif(showGif);
    setContentView(R.layout.__picker_activity_photo_picker);

    titlebar = (Titlebar) findViewById(R.id.titlebar);
    titlebar.init(this);


    maxCount = getIntent().getIntExtra(EXTRA_MAX_COUNT, DEFAULT_MAX_COUNT);
    columnNumber = getIntent().getIntExtra(EXTRA_GRID_COLUMN, DEFAULT_COLUMN_NUMBER);
    originalPhotos = getIntent().getStringArrayListExtra(EXTRA_ORIGINAL_PHOTOS);

    if(originalPhotos.size() != 0){
      maxCount = maxCount - originalPhotos.size();
    }

    pickerFragment = (PhotoPickerFragment) getSupportFragmentManager().findFragmentByTag("tag");
    if (pickerFragment == null) {
      pickerFragment = PhotoPickerFragment.newInstance(showCamera, showGif, previewEnabled, columnNumber, maxCount, originalPhotos);
      getSupportFragmentManager()
          .beginTransaction()
          .replace(R.id.container, pickerFragment, "tag")
          .commit();
      getSupportFragmentManager().executePendingTransactions();
    }

    //右边的点击事件
    titlebar.getTvRight().setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        ArrayList<String> photos = pickerFragment.getPhotoGridAdapter().getSelectedPhotoPaths();

        //1压缩,获取地址
        //2修改originnal
        if(photos != null && photos.size() >  0){
          for(int i=0; i< photos.size(); i++){
            if(!originalPhotos.contains(photos.get(i))){
              originalPhotos.add(photos.get(i));
            }
          }


          dialog = new ProgressDialog(getActivity(), ProgressDialog.THEME_HOLO_LIGHT);
          dialog.setMessage("处理中...");
          dialog.setCanceledOnTouchOutside(false);
          dialog.setCancelable(false);
          dialog.show();
          new Thread(new Runnable() {
            @Override
            public void run() {
              for(int i = 0; i< originalPhotos.size(); i++){
                if(originalPhotos.get(i).contains(Config.IS_SEVER_IMAGE)){//网络拉取图片，不用压缩，获取fid即可
                  intentList.add(originalPhotos.get(i));
                }else {
                  String path = BitmapUtil.getPath(originalPhotos.get(i));
                  intentList.add(path);
                }
              }

              Message msg = Message.obtain();
              handler.sendMessage(msg);
            }
          }).start();
        }else {
          Toast.makeText(getApplicationContext(),"还没有选择图片",Toast.LENGTH_SHORT).show();
        }
      }
    });

    pickerFragment.getPhotoGridAdapter().setOnItemCheckListener(new OnItemCheckListener() {
      @Override public boolean OnItemCheck(int position, Photo photo, final boolean isCheck, int selectedItemCount) {

        int total = selectedItemCount + (isCheck ? -1 : 1);

       // menuDoneItem.setEnabled(total > 0);


        if (maxCount <= 1) {
          List<Photo> photos = pickerFragment.getPhotoGridAdapter().getSelectedPhotos();
          if (!photos.contains(photo)) {
            photos.clear();
            pickerFragment.getPhotoGridAdapter().notifyDataSetChanged();
          }
          return true;
        }

        if (total > maxCount) {
          Toast.makeText(getActivity(), getString(R.string.__picker_over_max_count_tips, maxCount), LENGTH_LONG).show();
          return false;
        }
        titlebar.getTvRight().setText(getString(R.string.__picker_done_with_count, total, maxCount));
        return true;
      }
    });

  }

  private android.os.Handler handler = new android.os.Handler(){
    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      completeCompress();
    }
  };

  public void completeCompress(){
      dialog.dismiss();
      Intent intent = new Intent();
      intent.putStringArrayListExtra(KEY_SELECTED_PHOTOS, intentList);
      setResult(RESULT_OK, intent);
      finish();
  }


  /**
   * Overriding this method allows us to run our exit animation first, then exiting
   * the activity when it complete.
   */
  @Override public void onBackPressed() {
    if (imagePagerFragment != null && imagePagerFragment.isVisible()) {
      imagePagerFragment.runExitAnimation(new Runnable() {
        public void run() {
          if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
          }
        }
      });
    } else {
      super.onBackPressed();
    }
  }


  public void addImagePagerFragment(ImagePagerFragment imagePagerFragment) {
    this.imagePagerFragment = imagePagerFragment;
    getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.container, this.imagePagerFragment)
        .addToBackStack(null)
        .commit();
  }


  public PhotoPickerActivity getActivity() {
    return this;
  }

  public boolean isShowGif() {
    return showGif;
  }

  public void setShowGif(boolean showGif) {
    this.showGif = showGif;
  }
}
