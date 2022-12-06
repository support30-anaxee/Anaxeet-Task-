package com.example.aanaxee;

import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.io.File;
import java.io.IOException;
import java.util.Objects;


public class SignUpActivity extends AppCompatActivity {

    TextInputLayout regName, regUsername, regEmail, regPhone, regPassword;
    Button regBtn, regToLoginBtn;
    private DBHandler dbHandler;
    DBHandler obj = null;
    FirebaseDatabase rootNode;
    DatabaseReference reference;


    protected static final int CAMERA_REQUEST = 100;
    protected static final int GALLERY_PICTURE = 101;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 123;
    File captureMediaFile;
    ImageView gallery,camera;
    byte[] bytesDocumentsTypePicture = null;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        regName = findViewById(R.id.name);
        regUsername = findViewById(R.id.username);
        regEmail = findViewById(R.id.email);
        regPhone = findViewById(R.id.phoneNo);
        regPassword = findViewById(R.id.password);
        regBtn = findViewById(R.id.regBtn);
        regToLoginBtn = findViewById(R.id.loginbtn);
        gallery = findViewById(R.id.gallery);

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImage();
            }
        });

        dbHandler = new DBHandler(SignUpActivity.this);


        regToLoginBtn.setOnClickListener(view -> {

            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        regBtn.setOnClickListener(view -> {
            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("User");


            String name = Objects.requireNonNull(regName.getEditText()).getText().toString();
            String username = Objects.requireNonNull(regUsername.getEditText()).getText().toString();
            String email = Objects.requireNonNull(regEmail.getEditText()).getText().toString();
            String phone = Objects.requireNonNull(regPhone.getEditText()).getText().toString();
            String password = Objects.requireNonNull(regPassword.getEditText()).getText().toString();

            if (name.isEmpty() && username.isEmpty() && email.isEmpty() && phone.isEmpty() && password.isEmpty()) {
                Toast.makeText(SignUpActivity.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
            } else {

                //Data is Store in FireBase.................
                UserHelperClass helperClass = new UserHelperClass(name, username, email, phone, password);
                reference.child(username).setValue(helperClass);


                //Data is Store in SqualiteDB................
                boolean inserted = dbHandler.addNewUser(name, username, email, phone, password);
                if (inserted)
                    Toast.makeText(SignUpActivity.this, "Data has been added.", Toast.LENGTH_SHORT).show();
                regName.getEditText().setText("");
                regUsername.getEditText().setText("");
                regEmail.getEditText().setText("");
                regPhone.getEditText().setText("");
                regPassword.getEditText().setText("");

            }
        });
    }

    private boolean validationName() {
        String val = Objects.requireNonNull(regName.getEditText()).getText().toString();
        if (val.isEmpty()) {
            regName.setError("Field Cannot be Empty");
            return false;
        } else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validationUsername() {
        String val = Objects.requireNonNull(regUsername.getEditText()).getText().toString();
        if (val.isEmpty()) {
            regUsername.setError("Field Cannot be Empty");
            return false;
        } else {
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validationEmail() {
        String val = Objects.requireNonNull(regEmail.getEditText()).getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()) {
            regEmail.setError("Field Cannot be Empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            regEmail.setError("Invalid Email Address");
            return false;
        } else {
            regEmail.setError(null);
            return true;
        }
    }

    private boolean validationPhone() {
        String val = Objects.requireNonNull(regPhone.getEditText()).getText().toString();
        if (val.isEmpty()) {
            regPhone.setError("Field Cannot be Empty");
            return false;
        } else {
            regPhone.setError(null);
            return true;
        }
    }

    private boolean validationPassword() {
        String val = Objects.requireNonNull(regPassword.getEditText()).getText().toString();
        String password = "^" + "(?=.*[a-zA-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$)" + ".{4,}" + "$";
        if (val.isEmpty()) {
            regPassword.setError("Field Cannot be Empty");
            return false;
        } else if (!val.matches(password)) {
            regPassword.setError("Password is too weak");
            return false;
        } else {
            regPassword.setError(null);
            return true;
        }
    }

    public void registerUser(View view) {

        if (!validationName() | !validationPassword() | !validationPhone() | !validationEmail() | !validationUsername()) {
            return;
        }
        String name = Objects.requireNonNull(regName.getEditText()).getText().toString();
        String username = Objects.requireNonNull(regUsername.getEditText()).getText().toString();
        String email = Objects.requireNonNull(regEmail.getEditText()).getText().toString();
        String phone = Objects.requireNonNull(regPhone.getEditText()).getText().toString();
        String password = Objects.requireNonNull(regPassword.getEditText()).getText().toString();
        UserHelperClass helperClass = new UserHelperClass(name, username, email, phone, password);
        reference.child(username).setValue(helperClass);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST) {
            Bitmap bitmap = null;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                if (data != null) {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    bytesDocumentsTypePicture = new ImageUtils().getBytesFromBitmap(bitmap);
                } else {
                    Toast.makeText(getApplicationContext(), " some_error_while_uploading  ", Toast.LENGTH_SHORT).show();
                }
            } else {
                bitmap = BitmapFactory.decodeFile(captureMediaFile.getAbsolutePath());
                bytesDocumentsTypePicture = new ImageUtils().getBytesFromBitmap(bitmap);
            }

        } else if (resultCode == RESULT_OK && requestCode == GALLERY_PICTURE) {
            System.out.println("Data from gallery"+data);
            if (data != null) {
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse("selectedImage"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bytesDocumentsTypePicture = new ImageUtils().getBytesFromBitmap(bitmap);
            } else {
                Toast.makeText(getApplicationContext(), " some_error_while_uploading  ", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), " some_error_while_uploading  ", Toast.LENGTH_SHORT).show();
        }
    }

    private void getImage() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setMessage("How do you want to set your picture?");

        myAlertDialog.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, GALLERY_PICTURE);
            }
        });

        myAlertDialog.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

                captureMediaFile = ImageUtils.getOutputMediaFile(getApplicationContext());

                if (captureMediaFile != null) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                        intent.addFlags(FLAG_GRANT_READ_URI_PERMISSION | FLAG_GRANT_WRITE_URI_PERMISSION);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, captureMediaFile);
                    } else {
                        Uri photoUri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", captureMediaFile);
                        intent.addFlags(FLAG_GRANT_READ_URI_PERMISSION);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    }

                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(intent, CAMERA_REQUEST);
                    } else {
                        AlertDialog.Builder alert = new AlertDialog.Builder(SignUpActivity.this);
                        alert.setTitle(R.string.camera_unavailable);}
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(SignUpActivity.this);
                    alert.setMessage(R.string.file_save_error);
                }
            }
        });
        myAlertDialog.show();
    }

}


















