package org.d3if0021.assesment1.ui.screen

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if0021.assesment1.R
import org.d3if0021.assesment1.model.Buku
import org.d3if0021.assesment1.navigation.Screen
import org.d3if0021.assesment1.ui.theme.Assesment1Theme
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

private val data = getData()
private var index by mutableIntStateOf(0)

private fun getData(): List<Buku> {
    return listOf(
        Buku("Naruto", R.drawable.naruto, 15_000f),
        Buku("SlamDunk", R.drawable.slam_dunk, 30_000f),
        Buku("OnePiece", R.drawable.one_piece, 25_000f),
        Buku("SiJuki", R.drawable.si_juki, 60_000f),
        Buku("Oshinoko", R.drawable.oshi, 40_000f),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Assesment 1")
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.About.route)
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(R.string.tentang_aplikasi),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                navigationIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.new_logo),
                        contentDescription = "",
                        modifier = Modifier.size(85.dp)
                    )
                }
            )
        }
    ) { padding ->
        ScreenContent(Modifier.padding(padding))
    }
}


@Composable
fun ScreenContent(modifier: Modifier) {
    var nama by rememberSaveable { mutableStateOf("") }
    var namaEror by rememberSaveable { mutableStateOf(false) }
    val radioOptions = listOf(
        stringResource(id = R.string.digital),
        stringResource(id = R.string.cetak)
    )
    val context = LocalContext.current
    var jenis by rememberSaveable { mutableStateOf(radioOptions[0]) }
    Column(
        modifier = modifier
            .padding(12.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            value = nama,
            onValueChange = { nama = it },
            isError = namaEror,
            supportingText = { ErrorHint(namaEror) },
            trailingIcon = { IconPicker(namaEror, "") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { if (index != 0) index-- else index = data.size - 1 },
                modifier = modifier
                    .padding(15.dp)
                    .offset(y = (-20).dp),
                contentPadding = PaddingValues(15.dp)
            ) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "")
            }
            Image(
                painter = painterResource(id = data[index].imageResId),
                contentDescription = data[index].name,
                modifier = Modifier
                    .size(150.dp)
            )

            Button(
                onClick = { if (index != data.size - 1) index++ else index = 0 },
                modifier = modifier
                    .padding(15.dp)
                    .offset(y = (-20).dp),
                contentPadding = PaddingValues(15.dp)

            ) {
                Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = "")
            }
        }
        Text(text = formatCurrency(data[index].harga))
        Text(text = stringResource(id = R.string.tipe_buku))
        Row(
            modifier = Modifier
                .padding(top = 6.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
        ) {
            radioOptions.forEach { text ->
                JenisOption(
                    label = text,
                    isSelected = jenis == text,
                    modifier = Modifier
                        .selectable(
                            selected = jenis == text,
                            onClick = { jenis = text },
                            role = Role.RadioButton
                        )
                        .weight(1f)
                        .padding(16.dp)
                )
            }
        }
        var pesananDetail by remember { mutableStateOf("") }
        Button(
            onClick = {
                namaEror = (nama == "" || nama == "0")
                if (!namaEror) {
                    pesananDetail = "Nama: $nama\n" +
                            "Jenis Buku: $jenis\n" +
                            "Harga: ${formatCurrency(data[index].harga)}"
                }
            },
            modifier = Modifier.padding(top = 8.dp),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
        ) {
            Text(text = stringResource(R.string.pesan))
        }

        if (pesananDetail.isNotEmpty()) {
            Text(
                text = pesananDetail,
                modifier = Modifier.padding(top = 16.dp),
                fontWeight = FontWeight.Bold
            )
        }

        Divider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = 1.dp
            
        )
        Button(
            onClick = {
                      shareData(
                          context = context,
                          message = context.getString(R.string.bagikan_template,nama,jenis,data[index].harga))




            },
            modifier = Modifier.padding(top = 8.dp),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
        ) {
            Text(text = stringResource(R.string.bagikan))
        }
    }
}



@Composable
fun JenisOption(label: String, isSelected: Boolean, modifier: Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = isSelected, onClick = null)
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun GreetingPreview() {
    Assesment1Theme {
        MainScreen(rememberNavController())

    }
}

fun formatCurrency(amount: Float): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
    formatter.currency = Currency.getInstance("IDR")
    return formatter.format(amount)
}

@Composable
fun ErrorHint(isError: Boolean) {
    if (isError) {
        Text(text = stringResource(R.string.input_invalid))
    }
}
private fun shareData(context: Context, message: String){
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT,message)
    }
    if(shareIntent.resolveActivity(context.packageManager)!= null){
        context.startActivity(shareIntent)
    }
}

@Composable
fun IconPicker(isError: Boolean, unit: String) {
    if (isError) {
        Icon(imageVector = Icons.Filled.Warning, contentDescription = null)
    } else {
        Text(text = unit)
    }
}

