package concaro.hackernews.app.presentation.util;

/**
 * Created by Concaro on 1/12/2017.
 */

public class ImageUtil {

//    public static void loadImage(final Context ctx, final ImageView imageView, final String imageThumb, final String imageLarge, boolean isFitCenter) {
//        imageView.setImageBitmap(null);
//        Glide.clear(imageView);
//        if (!TextUtils.isEmpty(imageThumb) && !TextUtils.isEmpty(imageLarge)) {
//            Glide.with(ctx).load(imageLarge)
//                    .error(R.drawable.bg_loading_default)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .thumbnail(
//                            Glide.with(ctx).load(imageThumb)
//                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    )
//                    .animate(R.anim.alpha_on)
//                    .into(imageView);
//        } else if (!TextUtils.isEmpty(imageThumb)) {
//            Glide.with(ctx).load(imageThumb).placeholder(R.drawable.bg_loading_default).error(R.drawable.bg_loading_default)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .animate(R.anim.alpha_on)
//                    .into(imageView);
//        } else if (!TextUtils.isEmpty(imageLarge)) {
//            Glide.with(ctx).load(imageLarge).placeholder(R.drawable.bg_loading_default).error(R.drawable.bg_loading_default)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL).animate(R.anim.alpha_on)
//                    .animate(R.anim.alpha_on)
//                    .into(imageView);
//        } else {
//            // null for image
//            Glide.with(ctx).load(imageThumb).placeholder(R.drawable.bg_loading_default).error(R.drawable.bg_loading_default)
//                    .animate(R.anim.alpha_on)
//                    .animate(R.anim.alpha_on)
//                    .into(imageView);
//        }
//
//
//    }
//
//    public static void loadImage(Context ctx, String image, ImageView imageView) {
//        imageView.setImageBitmap(null);
//        Glide.clear(imageView);
//        Glide.with(ctx).load(image).placeholder(R.drawable.bg_loading_default).error(R.drawable.bg_loading_default)
//                .diskCacheStrategy(DiskCacheStrategy.ALL).animate(R.anim.alpha_on).into(imageView);
//    }
//
//    public static void loadImage(Context ctx, int image, ImageView imageView) {
//        imageView.setImageBitmap(null);
//        Glide.clear(imageView);
//        Glide.with(ctx).load(image).placeholder(R.drawable.bg_loading_default).error(R.drawable.bg_loading_default)
//                .diskCacheStrategy(DiskCacheStrategy.ALL).animate(R.anim.alpha_on).into(imageView);
//    }
//
//    public static void clearImage(ImageView imageView) {
//        imageView.setImageBitmap(null);
//        Glide.clear(imageView);
//    }
//
//    public static void loadAvatar(Context ctx, String image, ImageView imageView) {
//        imageView.setImageBitmap(null);
//        Glide.clear(imageView);
//        Glide.with(ctx).load(image).placeholder(R.drawable.avatar_pocca).error(R.drawable.avatar_pocca)
//                .diskCacheStrategy(DiskCacheStrategy.ALL).animate(R.anim.alpha_on).centerCrop().into(imageView);
//    }
//
//    public static void clearAvatar(ImageView imageView) {
//        imageView.setImageBitmap(null);
//        Glide.clear(imageView);
//    }

//    public static void showImageList(Context context, List<String> list, int position) {
//        GenericDraweeHierarchyBuilder hierarchyBuilder = GenericDraweeHierarchyBuilder.newInstance(context.getResources())
//                .setFailureImage(R.drawable.bg_fresco)
//                .setProgressBarImage(R.drawable.bg_fresco)
//                .setPlaceholderImage(R.drawable.bg_fresco);
//
//        new ImageViewer.Builder<>(context, list)
//                .setCustomDraweeHierarchyBuilder(hierarchyBuilder)
//                .setStartPosition(position)
//                .show();
//    }
}
