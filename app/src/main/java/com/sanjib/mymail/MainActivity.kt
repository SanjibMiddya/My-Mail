package com.sanjib.mymail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Black=Color(0xFF000000); private val Card=Color(0xFF0D0F12); private val Chip=Color(0xFF171A1F); private val Line=Color(0xFF292D33); private val Muted=Color(0xFF9299A5); private val Blue=Color(0xFF0A84FF)

data class Mail(val from:String,val initial:String,val subject:String,val preview:String,val time:String,val unread:Boolean)
private val mails=listOf(
 Mail("Janani Silva","J","Welcome to Salty Co.","I hope this message finds you well. We're excited for the journey ahead...","10:30 AM",true),
 Mail("Amanda Garcia","A","Project Update","Sharing the latest design files and notes...","9:20 AM",true),
 Mail("Microsoft","M","Security alert","New sign-in detected from Android","7:45 AM",false),
 Mail("HR Team","H","Annual Get-together","You're invited to our annual event...","Yesterday",false)
)

class MainActivity:ComponentActivity(){ override fun onCreate(b:Bundle?){super.onCreate(b);setContent{MyMail()}} }

@Composable fun MyMail(){
 var tab by remember{mutableStateOf(0)}; var opened by remember{mutableStateOf<Mail?>(null)}; var compose by remember{mutableStateOf(false)}
 MaterialTheme(colorScheme=darkColorScheme(background=Black,surface=Card,primary=Blue,onBackground=Color.White,onSurface=Color.White)){
  Surface(Modifier.fillMaxSize(),color=Black){
   when{compose->Compose({compose=false}); opened!=null->Detail(opened!!,{opened=null}); tab==0->Inbox({opened=it},{compose=true}); tab==1->Calendar(); tab==2->Simple("Contacts",listOf("Janani Silva","Amanda Garcia","Dinesh Kumar","HR Team")); tab==3->Simple("Tasks",listOf("Prepare project report","Team meeting slides","Client follow up","Review design files")); else->More()}
   if(!compose&&opened==null)Bottom(tab){tab=it}
  }
 }
}

@Composable private fun Header(title:String){Row(Modifier.fillMaxWidth().padding(16.dp,14.dp,10.dp,8.dp),verticalAlignment=Alignment.CenterVertically){Text(title,fontSize=34.sp,fontWeight=FontWeight.Bold,modifier=Modifier.weight(1f));IconButton({}){Icon(Icons.Default.Search,null)};IconButton({}){Icon(Icons.Default.MoreVert,null)}}}
@Composable private fun Search(){Row(Modifier.fillMaxWidth().padding(horizontal=16.dp).clip(RoundedCornerShape(24.dp)).background(Chip).padding(13.dp),verticalAlignment=Alignment.CenterVertically){Icon(Icons.Default.Search,null,tint=Muted,modifier=Modifier.size(18.dp));Spacer(Modifier.width(8.dp));Text("Search in mail",color=Muted)}}
@Composable private fun Chips(){Row(Modifier.horizontalScroll(rememberScrollState()).padding(16.dp,10.dp,16.dp,8.dp),horizontalArrangement=Arrangement.spacedBy(8.dp)){listOf("Primary","Work","Starred","Unread").forEachIndexed{i,s->Surface(color=if(i==0)Blue else Chip,shape=RoundedCornerShape(20.dp)){Text(s,color=if(i==0)Color.White else Muted,modifier=Modifier.padding(horizontal=15.dp,vertical=9.dp),fontSize=13.sp)}}}}
@Composable private fun Inbox(open:(Mail)->Unit,compose:()->Unit){Column(Modifier.fillMaxSize().padding(bottom=78.dp)){Header("Inbox");Search();Chips();Column(Modifier.verticalScroll(rememberScrollState()).weight(1f)){mails.forEach{m->Row(Modifier.fillMaxWidth().clickable{open(m)}.padding(15.dp),verticalAlignment=Alignment.Top){Box(Modifier.width(8.dp).padding(top=7.dp)){if(m.unread)Box(Modifier.size(7.dp).clip(CircleShape).background(Blue))};Avatar(m.initial);Spacer(Modifier.width(11.dp));Column(Modifier.weight(1f)){Row(Modifier.fillMaxWidth()){Text(m.from,fontWeight=FontWeight.Bold,modifier=Modifier.weight(1f));Text(m.time,color=Muted,fontSize=12.sp)};Text(m.subject);Text(m.preview,color=Muted,fontSize=12.sp,maxLines=1)}};HorizontalDivider(color=Line)}};FloatingActionButton(compose,Modifier.align(Alignment.End).padding(18.dp),containerColor=Blue){Icon(Icons.Default.Add,"Compose")}}}
@Composable private fun Avatar(s:String){Box(Modifier.size(42.dp).clip(CircleShape).background(Color(0xFF5A50F5)),contentAlignment=Alignment.Center){Text(s,fontWeight=FontWeight.Bold)}}
@Composable private fun Detail(m:Mail,back:()->Unit){Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())){Row(Modifier.padding(8.dp),verticalAlignment=Alignment.CenterVertically){IconButton(back){Icon(Icons.Default.ArrowBack,"Back")};Text("Mail",fontWeight=FontWeight.Bold);Spacer(Modifier.weight(1f));IconButton({}){Icon(Icons.Default.StarBorder,"Star")}};Text(m.subject,fontSize=24.sp,fontWeight=FontWeight.Bold,modifier=Modifier.padding(18.dp,8.dp));Row(Modifier.padding(horizontal=18.dp),verticalAlignment=Alignment.CenterVertically){Avatar(m.initial);Spacer(Modifier.width(10.dp));Column(Modifier.weight(1f)){Text(m.from,fontWeight=FontWeight.Bold);Text("${m.from.lowercase().replace(" ",".")}@company.com · To me",color=Muted,fontSize=12.sp)};Text(m.time,color=Muted,fontSize=12.sp)};Text("Hello,\n\nWelcome to Salty Co. I hope this message finds you well. On behalf of the entire team, I'm delighted to have you with us. We're excited for the journey ahead!\n\nBest regards,\nJanani Silva\nHR Manager\nSalty Co.",fontSize=15.sp,lineHeight=23.sp,modifier=Modifier.padding(18.dp));Surface(color=Chip,shape=RoundedCornerShape(10.dp),modifier=Modifier.padding(18.dp).fillMaxWidth()){Text("📎  Welcome.pdf · 124 KB",modifier=Modifier.padding(14.dp))};Row(Modifier.fillMaxWidth().padding(18.dp),horizontalArrangement=Arrangement.SpaceEvenly){Text("↩\nReply",color=Muted);Text("↗\nForward",color=Muted);Text("•••\nMore",color=Muted)}}}
@Composable private fun Compose(back:()->Unit){Column(Modifier.fillMaxSize().navigationBarsPadding()){Row(Modifier.padding(10.dp),verticalAlignment=Alignment.CenterVertically){TextButton(back){Text("Cancel",color=Blue)};Text("New Message",fontWeight=FontWeight.Bold,modifier=Modifier.weight(1f));Button(back){Text("Send")}};HorizontalDivider(color=Line);Column(Modifier.padding(18.dp).verticalScroll(rememberScrollState())){Field("From","sanjib@workmail.com");Field("To","recipient@example.com");Field("Cc / Bcc","");Field("Subject","Project update");Text("Hi,\n\nHere is the latest update.\n",modifier=Modifier.padding(top=18.dp));Text("Thanks & Regards,\nSanjib Middya\nHowrah Branch\nPh: 8334867432",color=Color(0xFFB8CFFF),lineHeight=23.sp);Spacer(Modifier.height(8.dp));Text("SHRIRAM Finance  |  SHRIRAM GREEN Finance",fontSize=12.sp,modifier=Modifier.background(Chip,RoundedCornerShape(9.dp)).padding(12.dp))};Spacer(Modifier.weight(1f));Row(Modifier.fillMaxWidth().background(Card).padding(12.dp),horizontalArrangement=Arrangement.spacedBy(22.dp)){Text("B");Text("I");Text("U");Text("🔗");Text("📎");Text("🖼")}}}
@Composable private fun Field(a:String,b:String){Column(Modifier.fillMaxWidth().padding(vertical=10.dp)){Text(a,color=Muted,fontSize=11.sp);Text(b);HorizontalDivider(color=Line,modifier=Modifier.padding(top=8.dp))}}
@Composable private fun Calendar(){Column(Modifier.fillMaxSize().padding(bottom=78.dp)){Header("April 2023");Row(Modifier.horizontalScroll(rememberScrollState()).padding(16.dp),horizontalArrangement=Arrangement.spacedBy(8.dp)){listOf("08 Mon","09 Tue","10 Wed","11 Thu","12 Fri").forEachIndexed{i,d->Surface(color=if(i==1)Blue else Chip,shape=RoundedCornerShape(18.dp)){Text(d,modifier=Modifier.padding(14.dp),fontSize=12.sp)}}};Text("Today · Tue, 9 Apr",color=Muted,fontWeight=FontWeight.Bold,modifier=Modifier.padding(18.dp));Event("Weekly Team Meeting","Conference Room 01 · 09:00–09:30");Event("Project Discussion","Microsoft Teams · 11:00–12:00");Event("Lunch with Amanda","Cinnamon Grand · 01:00–02:00")}}
@Composable private fun Event(a:String,b:String){Surface(color=Card,shape=RoundedCornerShape(14.dp),modifier=Modifier.fillMaxWidth().padding(8.dp,5.dp)){Column(Modifier.padding(14.dp)){Text(a,fontWeight=FontWeight.Bold);Text(b,color=Muted,fontSize=12.sp)}}}
@Composable private fun Simple(title:String,items:List<String>){Column(Modifier.fillMaxSize().padding(bottom=78.dp)){Header(title);Search();Surface(color=Card,shape=RoundedCornerShape(16.dp),modifier=Modifier.padding(14.dp).fillMaxWidth()){Column{items.forEachIndexed{i,x->Row(Modifier.fillMaxWidth().padding(14.dp),verticalAlignment=Alignment.CenterVertically){Avatar(x.first().toString());Spacer(Modifier.width(12.dp));Column{Text(x,fontWeight=FontWeight.Bold);Text(if(title=="Contacts")"${x.lowercase().replace(" ",".")}@company.com" else "Today",color=Muted,fontSize=11.sp)}};if(i<items.lastIndex)HorizontalDivider(color=Line)}}}}}
@Composable private fun More(){Column(Modifier.fillMaxSize().padding(bottom=78.dp)){Header("More");Surface(color=Card,shape=RoundedCornerShape(16.dp),modifier=Modifier.padding(14.dp).fillMaxWidth()){listOf("Mailboxes & Accounts","Settings","Signatures","Notifications","Appearance · AMOLED").forEach{Row(Modifier.fillMaxWidth().clickable{}.padding(16.dp)){Text(it,modifier=Modifier.weight(1f));Text("›",color=Muted)}}}}}
@Composable private fun Bottom(tab:Int,select:(Int)->Unit){Row(Modifier.fillMaxWidth().height(78.dp).background(Color(0xFF08090B)),horizontalArrangement=Arrangement.SpaceEvenly,verticalAlignment=Alignment.CenterVertically){listOf("Mail","Calendar","Contacts","Tasks","More").forEachIndexed{i,s->Column(Modifier.weight(1f).clickable{select(i)},horizontalAlignment=Alignment.CenterHorizontally){Text(when(i){0->"✉";1->"□";2->"♙";3->"✓";else->"•••"},color=if(tab==i)Blue else Muted,fontSize=19.sp);Text(s,color=if(tab==i)Blue else Muted,fontSize=10.sp)}}}}
